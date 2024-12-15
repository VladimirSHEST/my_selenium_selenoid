package com.ibs.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class S2 {
    private WebDriver driver;
    private Properties props;

    @BeforeEach
    void setUp() throws MalformedURLException {
        // Загрузка настроек из файла свойств
        props = new Properties();
        // Пример: загрузка из файла или системных переменных
        // props.load(new FileInputStream("config.properties"));

        // Определяем, где запускается тест (локально или удаленно)
        boolean isRemote = Boolean.parseBoolean(System.getProperty("remote", "true"));

        if (isRemote) {
            // Удаленный запуск через Selenoid
            DesiredCapabilities capabilities = new DesiredCapabilities();
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("browserName", "chrome"); // Указываем браузер
            selenoidOptions.put("browserVersion", "109.0"); // Указываем версию браузера
            selenoidOptions.put("enableVNC", true); // Включаем VNC
            selenoidOptions.put("enableVideo", false); // Отключаем запись видео
            capabilities.setCapability("selenoid:options", selenoidOptions);

            // Подключение к Selenoid
            driver = new RemoteWebDriver(
                    URI.create(props.getProperty("SELENOID_URL", "http://jenkins.applineselenoid.fvds.ru:4444/wd/hub")).toURL(),
                    capabilities
            );
        } else {
            // Локальный запуск
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    void testVegetableAdd() {
        driver.get("https://qualit.applineselenoid.fvds.ru/");  // открыли страницу

        /*
            Добавление неэкзотического овоща
        */

        WebElement sandBox = driver.findElement(By.xpath("//a[@class ='nav-link dropdown-toggle']"));
        sandBox.click(); // клик на песочницу

        WebElement goods = driver.findElement(By.xpath("//a[text()='Товары']"));
        goods.click(); // клик на товары

        WebElement listProducts = driver.findElement(By.xpath("//h5[text()='Список товаров']"));
        listProducts.isDisplayed(); // проверка, что открылась вкладка

        WebElement buttonAdd = driver.findElement(By.xpath("//button[@class='btn btn-primary' and @data-toggle='modal']"));
        buttonAdd.click(); //клик на кнопку добавить

        WebElement addProduct = driver.findElement(By.xpath("//*[@id='editModalLabel']"));
        addProduct.isDisplayed(); // проверка, что открылась вкладка

        WebElement productName = driver.findElement(By.xpath("//*[@id='name']"));
        productName.sendKeys("морковь");// Ввести значение в текстовое поле

        WebElement type = driver.findElement(By.xpath("//*[@id='type']"));
        type.click();  //клик на поле тип

        WebElement typeVegetable = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeVegetable.click(); // клик по типу овощ

        WebElement buttonSave = driver.findElement(By.xpath("//*[@id='save']"));
        buttonSave.click(); // клик по кнопке сохранить

        // проверка, что появился элемент в столбце
        driver.findElement(By.xpath("//th[.='5']/following-sibling::td[.='морковь']")).isDisplayed();

        /*
            Добавление экзотического овоща
        */

        WebElement buttonAdd2 = driver.findElement(By.xpath("//button[@class='btn btn-primary' and @data-toggle='modal']"));
        buttonAdd2.click(); //клик на кнопку добавить

        WebElement addProduct2 = driver.findElement(By.xpath("//*[@id='editModalLabel']"));
        addProduct2.isDisplayed(); // проверка, что открылась вкладка

        WebElement productName2 = driver.findElement(By.xpath("//*[@id='name']"));
        productName2.sendKeys("Момордика");// Ввести значение в текстовое поле

        WebElement type2 = driver.findElement(By.xpath("//*[@id='type']"));
        type2.click();  //клик на поле тип

        WebElement typeVegetable2 = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));
        typeVegetable2.click(); // клик по типу овощ

        WebElement checkExotic = driver.findElement(By.xpath("//*[@id='exotic']"));
        checkExotic.click(); // клик по чек-боксу экзотический

        WebElement buttonSave2 = driver.findElement(By.xpath("//*[@id='save']"));
        buttonSave2.click(); // клик по кнопке сохранить

        // проверка, что появился элемент Момордика в столбце
        driver.findElement(By.xpath("//th[.='6']/following-sibling::td[.='Момордика']")).isDisplayed();

        // удаляем данные
        WebElement sandBox2 = driver.findElement(By.xpath("//a[@class ='nav-link dropdown-toggle']"));
        sandBox2.click(); // клик на песочницу

        WebElement dataReset = driver.findElement(By.xpath("//*[@id='reset']"));
        dataReset.click(); // клик сброс данных
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
