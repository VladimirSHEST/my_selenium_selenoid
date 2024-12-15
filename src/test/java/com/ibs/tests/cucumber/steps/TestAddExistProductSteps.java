package com.ibs.tests.cucumber.steps;

import com.ibs.tests.cucumber.base_test.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;

import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestAddExistProductSteps extends BaseTest {


    private Statement statement;
    private ResultSet resultSet;

    @Before
    public void setUp() throws MalformedURLException {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Дано("заходим на страницу")
    public void заходим_на_страницу() {
        driver.get("https://qualit.applineselenoid.fvds.ru/");  // открыли страницу
    }

    @И("клик на вкладку Песочница")
    public void клик_на_вкладку() {
        WebElement sandBox = driver.findElement(By.xpath("//a[@class='nav-link dropdown-toggle']"));
        sandBox.click(); // клик на песочницу
    }

    @И("переход на вкладку {string}")
    public void переход_на_вкладку(String string) {
        WebElement goods = driver.findElement(By.xpath("//a[text()='" + string + "']"));
        goods.click(); // клик на товары
        WebElement listProducts = driver.findElement(By.xpath("//h5[text()='Список товаров']"));
        listProducts.isDisplayed(); // проверка, что открылась вкладка
    }

    @И("нажать кнопку Добавить")
    public void нажать_кнопку_Добавить() {
        WebElement buttonAdd = driver.findElement(By.xpath("//button[@class='btn btn-primary' and @data-toggle='modal']"));
        buttonAdd.click(); // клик на кнопку добавить
        WebElement addProduct = driver.findElement(By.xpath("//*[@id='editModalLabel']"));
        addProduct.isDisplayed(); // проверка, что открылась вкладка
    }

    @И("ввод название товара {string}")
    public void ввод_название_товара(String string) {
        WebElement productName = driver.findElement(By.xpath("//*[@id='name']"));
        productName.sendKeys(string); // Ввести значение в текстовое поле
    }

    @И("выбирали тип товара {string}")
    public void выбирали_тип_товара(String string) {
        WebElement type = driver.findElement(By.xpath("//*[@id='type']"));
        type.click(); // клик на поле тип
        WebElement typeFruit = driver.findElement(By.xpath("//option[@value='" + string + "']"));
        typeFruit.click(); // клик по типу фрукт
    }

    @И("нажать кнопку сохранить")
    public void нажать_кнопку() {
        WebElement buttonSave = driver.findElement(By.xpath("//*[@id='save']"));
        buttonSave.click(); // клик по кнопке сохранить
    }

    @Тогда("видим добавленный товар {string}")
    public void видим_добавленный_товар(String string) {
        // проверка, что появился элемент в столбце
        driver.findElement(By.xpath("//th[.='5']/following-sibling::td[.='" + string + "']")).isDisplayed();
    }

    @Тогда("удалям товар {string} через БД")
    public void удалям_товар_через_бд(String string) throws SQLException {
        // устанавливаем соединение с БД, передали УРЛ, логин и пароль
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb", "user", "pass");

        // Удаление товара из БД
        String deleteQuery = "DELETE FROM food WHERE food_name = '" + string + "'";
        statement = connection.createStatement();
        statement.executeUpdate(deleteQuery);
    }

    @Тогда("проверка, что товар {string} удален")
    public void проверка_что_товар_удален(String string) throws SQLException {
        // Проверка количества товаров после удаления
        String checkQuery = "SELECT COUNT(*) AS COUNT FROM food WHERE food_name = '" + string + "'";
        resultSet = statement.executeQuery(checkQuery);
        if (resultSet.next()) {
            int count = resultSet.getInt("COUNT");
            System.out.println("Кол-во товаров после удаления: " + count);
        } else {
            System.out.println("Кол-во товаров после удаления: 0");
        }
    }


}