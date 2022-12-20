package Exercises;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

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
        capabilities.setCapability("orientation", "PORTRAIT");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    /*
    Ex2: Создание метода
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

    /*
    Ex3: Тест: отмена поиска
    Написать тест, который:
    - Ищет какое-то слово
    - Убеждается, что найдено несколько статей
    - Отменяет поиск
    - Убеждается, что результат поиска пропал
    */

    @Test
    public void testShowSearchResultsAndCancel() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search…']"),
                "Java",
                "Cannot find 'Search…' input",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Island of Indonesia, Southeast Asia']"),
                "Cannot find 'Island of Indonesia, Southeast Asia' topic searching by 'Java'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='High-level programming language']"),
                "Cannot find 'High-level programming language' topic searching by 'Java'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'X' button",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Topic 'Object-oriented programming language' is still present on the page",
                5
        );

    }

    /*
    Ex4*: Тест: проверка слов в поиске
    Написать тест, который делает поиск по какому-то слову. Например, JAVA. Затем убеждается, что в каждом результате поиска есть это слово.
    Внимание, прокручивать результаты выдачи поиска не надо. Это мы научимся делать на следующих занятиях. Пока надо работать только с теми
    результатами поиска, который видны сразу, без прокрутки.
    */

    @Test
    public void testCheckWordInSearchResults() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search…']"),
                "Java",
                "Cannot find 'Search…' input",
                15
        );

        int count = getCountElements(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Elements not found",
                15
        );

        System.out.println("Total pages found: " + count);

        for (int i = 0; i < count; i++) {
            assertElementContainsText(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container' and @index='" + i + "']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                    "Java",
                    "Word 'Java' is absent on page [" + (i + 1) + "]"
            );
            System.out.println("Page [" + (i + 1) + "] contains word 'Java'");
        }

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

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private int getCountElements (By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        int count = driver.findElements(by).size();
        return count;
    }

    private WebElement assertElementContainsText(By by, String expectedValue, String errorMessage) {
        WebElement element = waitForElementPresent(by, "Element is absent");
        Assert.assertTrue(errorMessage, element.getAttribute("text").contains(expectedValue));
        return element;
    }

    /*
    Ex5: Тест: сохранение двух статей
    Написать тест, который:
        1. Сохраняет две статьи в одну папку
        2. Удаляет одну из статей
        3. Убеждается, что вторая осталась
        4. Переходит в неё и убеждается, что title совпадает
     */

    @Test
    public void testSaveTwoArticlesToMyList() throws InterruptedException {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search…']"),
                "Java",
                "Cannot find 'Search…' input",
                15
        );

        waitForElementAndOpenContextMenu(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot open context menu"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input field 'Name of this list'",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into field 'Name of this list'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                5
        );

        waitForElementAndOpenContextMenu(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Island of Indonesia, Southeast Asia']"),
                "Cannot open context menu"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot save article to reading list",
                5
        );

        waitForElementAndClick(
                By.className("android.widget.ImageButton"),
                "Unable to go to previous page, cannot find '<-' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );

        Thread.sleep(500);
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot press '" + name_of_folder + "' list",
                5
        );

        Thread.sleep(500);
        swipeElementToLeft(
                By.xpath("//*[@text='Java']"),
                "Cannot find saved article"
        );

        Thread.sleep(500);
        waitForElementAndClick(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article",
                5
        );

        String titleBeforeDelete = "Java (programming language)";
        String titleAfterDelete = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after delete article",
                titleBeforeDelete,
                titleAfterDelete
        );

    }

    /*
    Ex6: Тест: assert title
    Написать тест, который открывает статью и убеждается, что у нее есть элемент title. Важно: тест не должен дожидаться
    появления title, проверка должна производиться сразу. Если title не найден - тест падает с ошибкой.
    Метод можно назвать assertElementPresent.
    */
    @Test
    public void testAssertTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search…']"),
                "Java",
                "Cannot find 'Search…' input",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot open context menu",
                5
        );

        assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Title not present"
        );

    }

    /*
    Ex7*: Поворот экрана
    Appium устроен так, что может сохранить у себя в памяти поворот экрана, который использовался в предыдущем тесте,
    и начать новый тест с тем же поворотом. Мы написали тест на поворот экрана, и он может сломаться до того,
    как положение экрана восстановится. Следовательно, если мы запустим несколько тестов одновременно, последующие тесты
    будут выполняться в неправильном положении экрана, что может привести к незапланированным проблемам.
    Как нам сделать так, чтобы после теста на поворот экрана сам экран всегда оказывался в правильном положении,
    даже если тест упал в тот момент, когда экран был наклонен?
    */
    @Test
    public void testChangeScreenOrientationToLandscape() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[@text='Search…']"),
                searchLine,
                "Cannot find 'Search…' input",
                15
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + searchLine,
                15
        );

        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterRotation
        );

    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeElementToLeft (By by, String error_message) {
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

    private void waitForElementAndOpenContextMenu(By by, String error_message) {
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

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private void assertElementPresent (By by, String errorMessage) {
        if(driver.findElements(by).isEmpty()) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present.";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
