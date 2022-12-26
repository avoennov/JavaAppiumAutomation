package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name, '{TITLE}')]";
        REMOVE_FROM_SAVED = "id:Remove from saved";
        EDIT_BUTTON = "id:Edit";
        UNSAVE_BUTTON = "xpath://XCUIElementTypeStaticText[@name='Unsave']";
    }

    public iOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
