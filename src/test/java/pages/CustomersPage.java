package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CustomersPage {

    private final WebDriver driver;

    @FindBy(xpath = "//input[@ng-model=\"searchCustomer\"]")
    private WebElement searchTextField;
    @FindBy(xpath = "//tr[@ng-repeat=\"cust in Customers | orderBy:sortType:sortReverse | filter:searchCustomer\"]")
    private WebElement repeaterTableSearch;

    @FindBy(xpath = "//a[contains(text(),'\n" +
            "            First Name\n" +
            "            ')]")
    private WebElement firstNameColumn;

    @FindBy(xpath = "//tr")
    private List<WebElement> tableRows;

    public CustomersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step("Ввести текстовый запрос...")
    public void setTextToSearchTextField(String text) {
        searchTextField.sendKeys(text);
    }

    @Step("Найти клиента по имени...")
    public String getTextFromTable() {
        return repeaterTableSearch.getText();
    }

    @Step("Нажать на заголовок колонки таблицы First Name")
    public void clickFirstNameColumn() {
        firstNameColumn.click();
    }

    @Step("Проверить, отсортированы ли значения в таблице")
    public boolean assertIfTableSortedByFirstName() {
        LinkedList<String> names = new LinkedList<>();
        int i = 0;
        for (WebElement row : tableRows) {
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
