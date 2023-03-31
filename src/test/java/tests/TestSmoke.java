package tests;

import com.paulhammant.ngwebdriver.NgWebDriver;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import pages.BankManagerPage;
import utils.WebDriverFactory;

import java.time.Duration;


@Epic(value = "Тестирование API XYZ банк")
@Execution(ExecutionMode.CONCURRENT)
public class TestSmoke {

    protected static ThreadLocal<BankManagerPage> bankManagerPageInstance = new ThreadLocal<>();
    protected static ThreadLocal<WebDriver> driverInstance = new ThreadLocal<>();
    protected static ThreadLocal<NgWebDriver> ngWebDriverInstance = new ThreadLocal<>();

    @BeforeEach
    public void setup() {
        WebDriver driver = WebDriverFactory.getChromeDriver();
        driverInstance.set(driver);
        bankManagerPageInstance.set(new BankManagerPage(driver));
        ngWebDriverInstance.set(new NgWebDriver((JavascriptExecutor) driver));
        ngWebDriverInstance.get().waitForAngularRequestsToFinish();

        BankManagerPage bankManagerPage = getBankManagerPage();
        NgWebDriver ngWebDriver = getNgWebDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.navigate().to(bankManagerPage.getPageLink());
        ngWebDriver.waitForAngularRequestsToFinish();
    }

    protected WebDriver getDriver() {
        return driverInstance.get();
    }

    protected BankManagerPage getBankManagerPage() {
        return bankManagerPageInstance.get();
    }

    protected NgWebDriver getNgWebDriver() {
        return ngWebDriverInstance.get();
    }

    @Test
    @Issue("Case #1")
    @Feature(value = "Добавление нового пользователя")
    @Story(value = "Добавление пользователя Петя Петров")
    public void newCustomer() {
        WebDriver driver = getDriver();
        BankManagerPage bankManagerPage = getBankManagerPage();
        NgWebDriver ngWebDriver = getNgWebDriver();

        bankManagerPage.clickAddCustomerTabButton();
        ngWebDriver.waitForAngularRequestsToFinish();
        bankManagerPage.fillAddCustomerForm("Petya", "Petrov", "101000");
        bankManagerPage.clickAddCustomerButton();
        String actualMessage = driver.switchTo().alert().getText();
        String expectedMessage = "Customer added successfully with customer id";
        Assertions.assertTrue(actualMessage.startsWith(expectedMessage));
        driver.switchTo().alert().accept();

        bankManagerPage.clickCustomersTabButton();
        ngWebDriver.waitForAngularRequestsToFinish();
        bankManagerPage.setTextToSearchTextField("Petya");
        ngWebDriver.waitForAngularRequestsToFinish();
        String text = bankManagerPage.getTextFromTable();
        Assertions.assertTrue(text.contains("Petya Petrov"));
    }

    @Test
    @Issue("Case #2")
    @Feature(value = "Добавление нового пользователя")
    @Story(value = "Добавление пользователя-дубликата")
    public void duplicate() {
        WebDriver driver = getDriver();
        BankManagerPage bankManagerPage = getBankManagerPage();
        NgWebDriver ngWebDriver = getNgWebDriver();

        bankManagerPage.clickAddCustomerTabButton();
        ngWebDriver.waitForAngularRequestsToFinish();
        bankManagerPage.fillAddCustomerForm("Hermoine", "Granger", "E859AB");
        bankManagerPage.clickAddCustomerButton();
        String actualMessage = driver.switchTo().alert().getText();
        String expectedMessage = "Please check the details. Customer may be duplicate.";
        Assertions.assertTrue(actualMessage.startsWith(expectedMessage));
        driver.switchTo().alert().accept();
    }

    @Test
    @Issue("Case #3")
    @Feature(value = "Открытие счета")
    @Story(value = "Открытие счета для пользователя Hermoine Granger")
    public void openAccount() {
        WebDriver driver = getDriver();
        BankManagerPage bankManagerPage = getBankManagerPage();
        NgWebDriver ngWebDriver = getNgWebDriver();

        bankManagerPage.clickOpenAccountTabButton();
        ngWebDriver.waitForAngularRequestsToFinish();
        bankManagerPage.setNameAndCurrency("Hermoine Granger", "Dollar");
        bankManagerPage.clickProcessButton();
        String actualMessage = driver.switchTo().alert().getText();
        String expectedMessage = "Account created successfully with account Number";
        Assertions.assertTrue(actualMessage.startsWith(expectedMessage));
        driver.switchTo().alert().accept();
    }

    @Test
    @Issue("Case #4")
    @Feature(value = "Сортировка поисковой выдачи")
    @Story(value = "Сортировка клиентов по имени")
    public void sort() {
        BankManagerPage bankManagerPage = getBankManagerPage();
        NgWebDriver ngWebDriver = getNgWebDriver();

        bankManagerPage.clickCustomersTabButton();
        ngWebDriver.waitForAngularRequestsToFinish();
        bankManagerPage.clickFirstNameColumn();
        bankManagerPage.clickFirstNameColumn();
        Assertions.assertTrue(bankManagerPage.assertIfTableSortedByFirstName());
    }

    @Test
    @Issue("Case #5")
    @Feature(value = "Поиск клиентов")
    @Story(value = "Поиск клиента Harry по имени")
    public void search() {
        BankManagerPage bankManagerPage = getBankManagerPage();
        NgWebDriver ngWebDriver = getNgWebDriver();

        bankManagerPage.clickCustomersTabButton();
        ngWebDriver.waitForAngularRequestsToFinish();
        bankManagerPage.setTextToSearchTextField("Harry");
        Assertions.assertTrue(bankManagerPage.getTextFromTable().contains("Harry"));
    }

    @AfterEach
    public void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) driver.quit();
    }

}
