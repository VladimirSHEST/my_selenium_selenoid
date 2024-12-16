package com.ibs.tests.cucumber.steps;

import com.ibs.tests.cucumber.base_test.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class VegetableSteps extends BaseTest {

    @Before
    public void setUp() throws MalformedURLException {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Дано("открыть приложение")
    public void открыть_приложение() {
        driver.get("https://qualit.applineselenoid.fvds.ru/");
    }

    @Когда("переход на страницу Товары")
    public void переход_на_страницу_товары() {
        WebElement sandBox = driver.findElement(By.xpath("//a[@class='nav-link dropdown-toggle']"));
        sandBox.click();// клик на песочницу
        WebElement goods = driver.findElement(By.xpath("//a[text()='Товары']"));
        goods.click(); // клик на товары
        WebElement listProducts = driver.findElement(By.xpath("//h5[text()='Список товаров']"));
        listProducts.isDisplayed(); // проверка, что открылась вкладка
    }

    @И("нажать на кнопку Добавить")
    public void нажать_кнопку_добавить() {
        WebElement buttonAdd = driver.findElement(By.xpath("//button[@class='btn btn-primary' and @data-toggle='modal']"));
        buttonAdd.click(); //клик на кнопку добавить
        WebElement addProduct = driver.findElement(By.xpath("//*[@id='editModalLabel']"));
        addProduct.isDisplayed(); // проверка, что открылась вкладка
    }

    @И("ввод {string} как название продукта")
    public void ввод_как_название_продукта(String productName) {
        WebElement productNameField = driver.findElement(By.xpath("//*[@id='name']"));
        productNameField.sendKeys(productName);// Ввести значение в текстовое поле
    }

    @И("выбирать {string} как тип продукта")
    public void выбирать_как_тип_продукта(String productType) {
        WebElement typeField = driver.findElement(By.xpath("//*[@id='type']"));
        typeField.click();//клик на поле тип
        WebElement typeOption = driver.findElement(By.xpath("//option[@value='" + productType + "']"));
        typeOption.click();// клик по типу овощ
    }

    @И("отметить чекбокс Экзотический")
    public void отметить_чекбокс() {
        WebElement checkExotic = driver.findElement(By.xpath("//*[@id='exotic']"));
        checkExotic.click(); // клик по чек-боксу экзотический
    }

    @И("нажать кнопку {string}")
    public void нажать_кнопку_сохранить(String buttonText) {
        WebElement buttonSave = driver.findElement(By.xpath("//*[@id='" + buttonText + "']"));
        buttonSave.click(); // клик по кнопке сохранить
    }

    @Тогда("должны увидеть {string} в списке товаров")
    public void должны_увидеть_в_списке_товаров(String productName) {
        WebElement productElement = driver.findElement
                (By.xpath("//th[.='5']/following-sibling::td[.='" + productName + "']"));
        assertTrue(productElement.isDisplayed());// проверка, что появился элемент в столбце
    }

    @Когда("сброс данных")
    public void сброс_данных() {
        WebElement sandBox2 = driver.findElement(By.xpath("//a[@class ='nav-link dropdown-toggle']"));
        sandBox2.click(); // клик на песочницу
        WebElement dataReset = driver.findElement(By.xpath("//*[@id='reset']"));
        dataReset.click(); // клик сброс данных
    }

}
