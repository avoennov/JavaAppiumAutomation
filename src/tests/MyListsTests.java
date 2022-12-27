package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static String
            name_of_folder = "Learning programming",
            search_line = "Java",
            article_footer_1 = "Object-oriented programming language",
            article_footer_2 = "Island in Indonesia";

    @Test
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(article_footer_1);

        Thread.sleep(500);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
            ArticlePageObject.closeArticle();
        } else {
            ArticlePageObject.addArticlesToMySaved();
            ArticlePageObject.closeArticle();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();
        if (Platform.getInstance().isIOS()) {
            NavigationUI.closePopUpDialog();
        }

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        Thread.sleep(500);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    /*
    Ex11: Рефакторинг тестов
    Адаптировать под iOS тест на удаление одной сохраненной статьи из двух. Вместо проверки title-элемента придумать другой
    способ верификации оставшейся статьи (т.е. способ убедиться, что осталась в сохраненных ожидаемая статья).
    Результат выполнения задания нужно закоммитить в git. В качестве ответа прислать ссылку на коммит. Если вам потребовалось
    несколько коммитов для выполнения одного задания - присылайте ссылки на все эти коммиты с комментариями.*/
    @Test
    public void testSaveTwoArticlesToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring(article_footer_1);

        Thread.sleep(500);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_footer = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.clickBackButton();
        SearchPageObject.clickByArticleWithSubstring(article_footer_2);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();
        if (Platform.getInstance().isIOS()) {
            NavigationUI.closePopUpDialog();
        }

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        MyListsPageObject.swipeByArticleFooterToDelete(article_footer_2);
        Thread.sleep(500);
        MyListsPageObject.waitForArticleToAppearByFooter(article_footer_1);
    }
}
