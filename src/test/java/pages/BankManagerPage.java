package pages;

import com.paulhammant.ngwebdriver.ByAngularModel;
import com.paulhammant.ngwebdriver.ByAngularPartialButtonText;
import com.paulhammant.ngwebdriver.ByAngularRepeater;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BankManagerPage {

    private final WebDriver driver;
    private final String PAGE_LINK = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";
    @ByAngularPartialButtonText.FindBy(partialButtonText = "Add Customer")
    private WebElement addCustomerTabButton;
    @ByAngularPartialButtonText.FindBy(partialButtonText = "Open Account")
    private WebElement openAccountTabButton;
    @ByAngularPartialButtonText.FindBy(partialButtonText = "Customers")
    private WebElement customersTabButton;
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameTextField;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameTextField;
    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private WebElement postCodeTextField;
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Add Customer')]")
    private WebElement addCustomerButton;
    @ByAngularModel.FindBy(model = "custId")
    private WebElement customerNameDropDownMenu;
    @ByAngularModel.FindBy(model = "currency")
    private WebElement currencyDropDownMenu;
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Process')]")
    private WebElement processButton;
    @ByAngularModel.FindBy(model = "searchCustomer")
    private WebElement searchTextField;

    @ByAngularRepeater.FindBy(rootSelector = "#application-container", repeater = "cust in Customers | orderBy:sortType:sortReverse | filter:searchCustomer", exact = false)
    private WebElement repeaterTableSearch;

    @FindBy(xpath = "//a[contains(text(),'\n" +
            "            First Name\n" +
            "            ')]")
    private WebElement firstNameColumn;

    public BankManagerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String getPageLink() {
        return PAGE_LINK;
    }

    public void setFirstNameTextField(String firstName) {
        firstNameTextField.sendKeys(firstName);
    }

    public void setLastNameTextField(String lastName) {
        lastNameTextField.sendKeys(lastName);
    }

    public void setPostCodeTextField(String postCode) {
        postCodeTextField.sendKeys(postCode);
    }

    @Step("Выбрать клиента, открывающего счет")
    public void setCustomerNameInDropDownMenu(String name) {
        customerNameDropDownMenu.click();
        Select s = new Select(customerNameDropDownMenu);
        s.selectByVisibleText(name);
    }

    @Step("Выбрать валюту вклада")
    public void setCurrencyInDropDownMenu(String currency) {
        currencyDropDownMenu.click();
        Select s = new Select(currencyDropDownMenu);
        s.selectByVisibleText(currency);
    }

    @Step("Ввести текстовый запрос...")
    public void setTextToSearchTextField(String text) {
        searchTextField.sendKeys(text);
    }

    @Step("Найти клиента по имени...")
    public String getTextFromTable() {
        return repeaterTableSearch.getText();
    }

    @Step("Нажать кнопку 'Add Customer' основного меню")
    public void clickAddCustomerTabButton() {
        addCustomerTabButton.click();
    }

    @Step("Нажать кнопку 'Open Account' основного меню")
    public void clickOpenAccountTabButton() {
        openAccountTabButton.click();
    }

    @Step("Нажать кнопку 'Customers' основного меню")
    public void clickCustomersTabButton() {
        customersTabButton.click();
    }

    @Step("Отправка формы добавления нового клиента")
    public void clickAddCustomerButton() {
        addCustomerButton.click();
    }

    @Step("Отправка формы открытия счета")
    public void clickProcessButton() {
        processButton.click();
    }

    @Step("Нажать на заголовок колонки таблицы First Name")
    public void clickFirstNameColumn() {
        firstNameColumn.click();
    }

    @Step("Заполнить поля формы добавления нового клиента")
    public void fillAddCustomerForm(String firstName, String lastName, String postCode) {
        setFirstNameTextField(firstName);
        setLastNameTextField(lastName);
        setPostCodeTextField(postCode);
    }

    @Step("Заполнить поля формы открытия счета")
    public void setNameAndCurrency(String name, String currency) {
        setCustomerNameInDropDownMenu(name);
        setCurrencyInDropDownMenu(currency);
    }

    @Step("Проверить, отсортированы ли значения в таблице")
    public boolean assertIfTableSortedByFirstName() {
        List<WebElement> rows = driver.findElements(By.cssSelector("tr"));
        LinkedList<String> names = new LinkedList<>();
        int i = 0;
        for (WebElement row : rows) {
            if (i != 0) {
                names.add(row.getText().split(" ")[0]);
            }
            i++;
        }
        List<String> sortedNames = new LinkedList<>(names);
        Collections.sort(sortedNames);
        return sortedNames.equals(names);
    }

}
