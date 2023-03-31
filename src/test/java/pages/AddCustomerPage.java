package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage {

    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameTextField;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameTextField;
    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private WebElement postCodeTextField;
    @FindBy(xpath = "//button[@type='submit' and contains(text(),'Add Customer')]")
    private WebElement addCustomerButton;

    public AddCustomerPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
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

    @Step("Отправка формы добавления нового клиента")
    public void clickAddCustomerButton() {
        addCustomerButton.click();
    }

    @Step("Заполнить поля формы добавления нового клиента")
    public void fillAddCustomerForm(String firstName, String lastName, String postCode) {
        setFirstNameTextField(firstName);
        setLastNameTextField(lastName);
        setPostCodeTextField(postCode);
    }

}
