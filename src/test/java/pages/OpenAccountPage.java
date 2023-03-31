package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OpenAccountPage {

    @FindBy(xpath = "//select[@ng-model=\"custId\"]")
    private WebElement customerNameDropDownMenu;
    @FindBy(xpath = "//select[@ng-model=\"currency\"]")
    private WebElement currencyDropDownMenu;
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Process')]")
    private WebElement processButton;

    public OpenAccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
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

    @Step("Отправка формы открытия счета")
    public void clickProcessButton() {
        processButton.click();
    }

    @Step("Заполнить поля формы открытия счета")
    public void setNameAndCurrency(String name, String currency) {
        setCustomerNameInDropDownMenu(name);
        setCurrencyInDropDownMenu(currency);
    }

}
