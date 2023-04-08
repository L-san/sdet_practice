package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BankManagerPage {

    private final WebDriver driver;
    private final String PAGE_LINK = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";
    @FindBy(xpath = "//button[@ng-click=\"addCust()\"]")
    private WebElement addCustomerTabButton;
    @FindBy(xpath = "//button[@ng-click=\"openAccount()\"]")
    private WebElement openAccountTabButton;
    @FindBy(xpath = "//button[@ng-click=\"showCust()\"]")
    private WebElement customersTabButton;

    public BankManagerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String getPageLink() {
        return PAGE_LINK;
    }

    @Step("Нажать кнопку 'Add Customer' основного меню")
    public AddCustomerPage clickAddCustomerTabButton() {
        addCustomerTabButton.click();
        return new AddCustomerPage(driver);
    }

    @Step("Нажать кнопку 'Open Account' основного меню")
    public OpenAccountPage clickOpenAccountTabButton() {
        openAccountTabButton.click();
        return new OpenAccountPage(driver);
    }

    @Step("Нажать кнопку 'Customers' основного меню")
    public CustomersPage clickCustomersTabButton() {
        customersTabButton.click();
        return new CustomersPage(driver);
    }

}
