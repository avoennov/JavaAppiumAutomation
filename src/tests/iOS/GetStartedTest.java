package tests.iOS;

import lib.iOSCoreTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends iOSCoreTestCase {

    @Test
    public void testPassThroughWelcome() {
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWaysToExploreText();
        WelcomePage.clickNextButton();

        WelcomePage.waitAddOrEditPreferredLanguagesText();
        WelcomePage.clickNextButton();

        WelcomePage.waitLearnMoreAboutDataCollectedText();
        WelcomePage.clickGetStartedButton();
    }
}
