package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCancelSearchWithClear() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "Sabaton discography";
        SearchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResults = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "Search results not found",
                amountOfSearchResults > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "sfsfsfsffs";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    /*
    Ex12*: Рефакторинг тестов
    Адаптировать по iOS тест на поиск и верификацию трех результатов выдачи поиска.
    */
    @Test
    public void testCheckResultWithTitleAndDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia, Southeast Asia");
        } else {
            SearchPageObject.waitForElementByTitleAndDescription("Java", "Island in Indonesia");
        }
        SearchPageObject.waitForElementByTitleAndDescription("JavaScript", "High-level programming language");
        SearchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }
}
