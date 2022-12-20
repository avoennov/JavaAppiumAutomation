package Exercises.HWlib.HWui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HWMainPageObject {

    protected AppiumDriver driver;

    public HWMainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement assertElementHasText(By by, String expectedValue, String errorMessage) {
        WebElement element = waitForElementPresent(by, "Element is absent");
        String actualValue = element.getAttribute("text");

        Assert.assertEquals(
                errorMessage,
                expectedValue,
                actualValue
        );

        return element;
    }

    public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public int getCountElements (By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        int count = driver.findElements(by).size();
        return count;
    }

    public WebElement assertElementContainsText(By by, String expectedValue, String errorMessage) {
        WebElement element = waitForElementPresent(by, "Element is absent");
        Assert.assertTrue(errorMessage, element.getAttribute("text").contains(expectedValue));
        return element;
    }

    public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void swipeElementToLeft (By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                //.press(right_x, middle_y)
                //.waitAction(300)
                //.moveTo(left_x, middle_y)
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();

    }

    public void waitForElementAndOpenContextMenu(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        TouchAction action = new TouchAction(driver);
        action
                //.longPress(element)
                .longPress((LongPressOptions) element)
                .perform();
    }

    public String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void assertElementPresent (By by, String errorMessage) {
        if(driver.findElements(by).isEmpty()) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present.";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
