package Exercises;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class HomeWork {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("apPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    /*Ex2: Создание метода
    Необходимо написать функцию, которая проверяет наличие ожидаемого текста у элемента. Предлагается назвать ее assertElementHasText.
    На вход эта функция должна принимать локатор элемент, ожидаемый текст и текст ошибки, который будет написан в случае,
    если элемент по этому локатору не содержит текст, который мы ожидаем.

    Также, необходимо написать тест, который проверяет, что поле ввода для поиска статьи содержит текст (в разных версиях
    приложения это могут быть тексты "Search..." или "Search Wikipedia", правильным вариантом следует считать тот, которые есть сейчас).
    Очевидно, что тест должен использовать написанный ранее метод.
    */

    @Test
    public void testSearchFieldHasText() {
        assertElementHasText(
                By.className("android.widget.TextView"),
                "Search Wikipedia",
                "Cannot find 'Search Wikipedia' input"
        );

    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement assertElementHasText(By by, String expectedValue, String errorMessage) {
        WebElement element = waitForElementPresent(by, "Element is absent");
        String actualValue = element.getAttribute("text");

        Assert.assertEquals(
                errorMessage,
                expectedValue,
                actualValue
        );

        return element;
    }
}
