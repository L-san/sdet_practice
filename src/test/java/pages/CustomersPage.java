package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomersPage {

    @FindBy(xpath = "//input[@ng-model=\"searchCustomer\"]")
    private WebElement searchTextField;

    @FindBy(xpath = ".//table/thead/tr/td")
    private List<WebElement> tableColNames;

    @FindBy(xpath = "//a[@ng-click=\"sortType = 'fName'; sortReverse = !sortReverse\"]")
    private WebElement firstNameColumn;

    @FindBy(xpath = ".//table/tbody/tr")
    private List<WebElement> tableRows;

    public CustomersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @Step("Ввести текстовый запрос...")
    public void setTextToSearchTextField(String text) {
        searchTextField.sendKeys(text);
    }

    @Step("Получить запись в таблице из строки c номером nRow")
    public String getTextFromRow(int nRow) {
        return tableRows.get(nRow).getText();
    }

    @Step("Нажать на заголовок колонки таблицы First Name")
    public void clickFirstNameColumn() {
        firstNameColumn.click();
    }

    @Step("Получить заголовки колонок таблицы")
    public ArrayList<String> getColumnNames(){
        return tableColNames.stream().map(WebElement::getText).collect(Collectors.toCollection(ArrayList::new));
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
