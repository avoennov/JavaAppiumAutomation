package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
        MY_LISTS_LINK,
        CLOSE_POP_UP_DIALOG;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find 'My lists' button",
                5
        );
    }

    public void closePopUpDialog() {
        this.waitForElementAndClick(
                CLOSE_POP_UP_DIALOG,
                "Cannot find and close pop-up dialog",
                5
        );
    }
}
