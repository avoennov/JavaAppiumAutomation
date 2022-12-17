package Exercises.HWlib.HWui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class HWArticlePageObject extends HWMainPageObject{

    private static final String
        ARTICLE_FOOTER = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        ADD_TO_LIST = "//*[@text='Add to reading list']",
        GOT_IT = "org.wikipedia:id/onboarding_button",
        LIST_NAME_FIELD = "org.wikipedia:id/text_input",
        OK_BUTTON = "//*[@text='OK']",
        BACK_BUTTON = "android.widget.ImageButton",
        MY_LISTS_BUTTON = "//android.widget.FrameLayout[@content-desc='My lists']",
        NAME_OF_ELEMENT = "//*[@text='{SUBSTRING}']",
        TITLE = "org.wikipedia:id/view_page_title_text";

    public HWArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getArticleFooterElement(String substring) {
        return ARTICLE_FOOTER.replace("{SUBSTRING}", substring);
    }
    
    private static String getNameOfElement(String substring) {
        return NAME_OF_ELEMENT.replace("{SUBSTRING}", substring);
    }
    /*TEMPLATES METHODS*/

    public void openContextMenu(String substring) {
        String article_footer_xpath = getArticleFooterElement(substring);
        this.waitForElementAndOpenContextMenu(By.xpath(article_footer_xpath), "Cannot open context menu");
    }

    public void addToMyList() {
        this.waitForElementAndClick(By.xpath(ADD_TO_LIST), "Cannot find option to add article to reading list", 5);
    }

    public void clickGotIt() {
        this.waitForElementAndClick(By.id(GOT_IT), "Cannot find and click 'Got it' tip overlay", 5);
    }

    public void clearDefaultNameOfList() {
        this.waitForElementAndClear(By.id(LIST_NAME_FIELD), "Cannot find clear input field 'Name of this list'", 5);
    }

    public void setNewNameOfList(String name_of_folder) {
        this.waitForElementAndSendKeys(By.id(LIST_NAME_FIELD), name_of_folder, "Cannot put text into field 'Name of this list'", 5);
    }

    public void clickOkBtn() {
        this.waitForElementAndClick(By.xpath(OK_BUTTON), "Cannot press 'OK' button", 5);
    }
    
    public void selectListByName(String name_of_folder) {
        String list_name_xpath = getNameOfElement(name_of_folder);
        this.waitForElementAndClick(By.xpath(list_name_xpath), "Cannot save article to reading list", 5);
    }

    public void clickGoBackButton() {
        this.waitForElementAndClick(By.className(BACK_BUTTON), "Unable to go to previous page, cannot find '<-' button", 5);
    }
    
    public void clickMyListsButton() {
        this.waitForElementAndClick(By.xpath(MY_LISTS_BUTTON), "Cannot find 'My lists' button", 5
        );
    }
    
    public void swipeElementToLeft(String name_of_element) {
        String name_of_element_xpath = getNameOfElement(name_of_element);
        this.swipeElementToLeft(By.xpath(name_of_element_xpath), "Cannot find element");
    }

    public void clickOnElement(String name_of_element) {
        String name_of_element_xpath = getNameOfElement(name_of_element);
        this.waitForElementAndClick(By.xpath(name_of_element_xpath), "Cannot find element", 5);
    }

    public String getTextAttribute() {
        return this.waitForElementAndGetAttribute(By.id(TITLE), "text", "Cannot find title of article", 15);
    }

}
