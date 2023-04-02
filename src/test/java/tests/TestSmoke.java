package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebDriver;
import pages.AddCustomerPage;
import pages.BankManagerPage;
import pages.CustomersPage;
import pages.OpenAccountPage;
import utils.WebDriverFactory;

import java.time.Duration;


@Epic(value = "Тестирование API XYZ банк")
@Execution(ExecutionMode.CONCURRENT)
public class TestSmoke {

    protected static ThreadLocal<BankManagerPage> bankManagerPageInstance = new ThreadLocal<>();
    protected static ThreadLocal<WebDriver> driverInstance = new ThreadLocal<>();
    private final String FIRST_NAME = "Petya";
    private final String LAST_NAME = "Petrov";
    private final String POST_CODE = "101000";

    protected WebDriver getDriver() {
        return driverInstance.get();
    }

    protected BankManagerPage getBankManagerPage() {
        return bankManagerPageInstance.get();
    }

    @BeforeEach
    public void setup() throws InterruptedException {
        Thread.sleep(1000);
        WebDriver driver = WebDriverFactory.getChromeDriver();
        driverInstance.set(driver);
        bankManagerPageInstance.set(new BankManagerPage(driver));
        BankManagerPage bankManagerPage = getBankManagerPage();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.navigate().to(bankManagerPage.getPageLink());
    }

    @Test
    @Feature(value = "Добавление нового пользователя")
    @Story(value = "Добавление пользователя")
    public void newCustomer() {
        WebDriver driver = getDriver();
        addCustomer(FIRST_NAME, LAST_NAME, POST_CODE);

        String actualMessage = driver.switchTo().alert().getText();
        String expectedMessage = "Customer added successfully with customer id";
        Assertions.assertTrue(actualMessage.startsWith(expectedMessage));
        driver.switchTo().alert().accept();

        assertIfSearchRequestContains("Petya","Petya Petrov");
    }

    @Test
    @Feature(value = "Добавление нового пользователя")
    @Story(value = "Добавление пользователя-дубликата")
    public void duplicate() {
        WebDriver driver = getDriver();
        addCustomerAndSwitchAlert(FIRST_NAME, LAST_NAME, POST_CODE);

        addCustomer(FIRST_NAME, LAST_NAME, POST_CODE);
        String actualMessage = driver.switchTo().alert().getText();
        String expectedMessage = "Please check the details. Customer may be duplicate.";
        Assertions.assertTrue(actualMessage.startsWith(expectedMessage));
        driver.switchTo().alert().accept();
    }

    @Test
    @Feature(value = "Открытие счета")
    @Story(value = "Открытие счета для пользователя Petya Petrov")
    public void openAccount() {
        WebDriver driver = getDriver();
        BankManagerPage bankManagerPage = getBankManagerPage();
        addCustomerAndSwitchAlert(FIRST_NAME, LAST_NAME, POST_CODE);

        OpenAccountPage openAccountPage = bankManagerPage.clickOpenAccountTabButton();
        openAccountPage.setNameAndCurrency("Petya Petrov", "Dollar");
        openAccountPage.clickProcessButton();
        String actualMessage = driver.switchTo().alert().getText();
        String expectedMessage = "Account created successfully with account Number";
        Assertions.assertTrue(actualMessage.startsWith(expectedMessage));
        driver.switchTo().alert().accept();

        String[] ids = actualMessage.split(" :");
        assertIfSearchRequestContains("Petya",ids[ids.length-1]);
    }

    @Test
    @Feature(value = "Сортировка поисковой выдачи")
    @Story(value = "Сортировка клиентов по имени")
    public void sort() {
        BankManagerPage bankManagerPage = getBankManagerPage();
        CustomersPage customersPage = bankManagerPage.clickCustomersTabButton();
        customersPage.clickFirstNameColumn();
        customersPage.clickFirstNameColumn();
        Assertions.assertTrue(customersPage.assertIfTableSortedByFirstName());
    }

    @Test
    @Feature(value = "Поиск клиентов")
    @Story(value = "Поиск клиента Petya по имени")
    public void search() {
        addCustomerAndSwitchAlert(FIRST_NAME, LAST_NAME, POST_CODE);
        assertIfSearchRequestContains("Petya","Petya");
    }

    @AfterEach
    public void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) driver.quit();
    }

    private void addCustomer(String firstName, String lastName, String postCode) {
        BankManagerPage bankManagerPage = getBankManagerPage();
        AddCustomerPage addCustomerPage = bankManagerPage.clickAddCustomerTabButton();
        addCustomerPage.fillAddCustomerForm(firstName, lastName, postCode);
        addCustomerPage.clickAddCustomerButton();
    }

    private void addCustomerAndSwitchAlert(String firstName, String lastName, String postCode) {
        WebDriver driver = getDriver();
        addCustomer(firstName, lastName, postCode);
        driver.switchTo().alert().accept();
    }

    private void assertIfSearchRequestContains(String request, String substring){
        BankManagerPage bankManagerPage = getBankManagerPage();
        CustomersPage customersPage = bankManagerPage.clickCustomersTabButton();
        customersPage.setTextToSearchTextField(request);
        Assertions.assertTrue(customersPage.getTextFromTable().contains(substring));
    }

}
