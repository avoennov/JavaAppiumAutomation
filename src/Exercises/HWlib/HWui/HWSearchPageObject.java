package Exercises.HWlib.HWui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class HWSearchPageObject extends HWMainPageObject {

    private static int i = 0;

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "//*[@text='Search…']",
        SEARCH_RESULT = "org.wikipedia:id/page_list_item_container",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "//*[@text='{TITLE}' and //@text='{DESCRIPTION}']/parent::*",
        SEARCH_RESULT_TITLE = "//*[@resource-id='org.wikipedia:id/page_list_item_container' and @index='" + i + "']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_TEXT_VIEW = "android.widget.TextView",
        TITLE = "org.wikipedia:id/view_page_title_text";

    public HWSearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /*TEMPLATES METHODS*/

    public void initSearchInput() {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element");
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String search_result_xpath = getResultSearchByTitleAndDescription(title, description);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with title '" + title + "' and description '" + description + "'");
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public void waitForSearchResultDisappear(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementNotPresent(By.xpath(search_result_xpath), "Search result with substring '" + substring + "' is present", 5);
    }

    public void checkEachSearchResultContainsText(String search_line) {
        int count = this.getCountElements(By.id(SEARCH_RESULT), "Elements not found", 15);

        System.out.println("Total pages found: " + count);

        for (int i = 0; i < count; i++) {
            this.assertElementContainsText(By.xpath(SEARCH_RESULT_TITLE), search_line, "Word '" + search_line + "' is absent on page [" + (i + 1) + "]");
            System.out.println("Page [" + (i + 1) + "] contains word '" + search_line + "'");
        }
    }

    public void searchElementHasText(String expectedText) {
        this.assertElementHasText(By.className(SEARCH_TEXT_VIEW), expectedText, "Cannot find 'Search Wikipedia' input");
    }

    public void clickByArticleWithSubstring(String substring) {
        String element_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(element_xpath), "Cannot find and click on element", 5);
    }

    public void checkElementIsPresentNoWait() {
        this.assertElementPresent(By.id(TITLE), "Title not present");
    }
}
