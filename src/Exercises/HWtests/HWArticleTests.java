package Exercises.HWtests;

import Exercises.HWlib.HWCoreTestCase;
import Exercises.HWlib.HWui.HWArticlePageObject;
import Exercises.HWlib.HWui.HWSearchPageObject;
import org.junit.Assert;
import org.junit.Test;

public class HWArticleTests extends HWCoreTestCase {

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
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.initSearchInput();
        HWSearchPageObject.typeSearchLine("Java");

        HWArticlePageObject HWArticlePageObject = new HWArticlePageObject(driver);
        HWArticlePageObject.openContextMenu("Object-oriented programming language");
        HWArticlePageObject.addToMyList();
        HWArticlePageObject.clickGotIt();
        HWArticlePageObject.clearDefaultNameOfList();
        HWArticlePageObject.setNewNameOfList("Learning programming");
        HWArticlePageObject.clickOkBtn();
        HWArticlePageObject.openContextMenu("Island of Indonesia, Southeast Asia");
        HWArticlePageObject.addToMyList();
        HWArticlePageObject.selectListByName("Learning programming");
        HWArticlePageObject.clickGoBackButton();
        HWArticlePageObject.clickMyListsButton();
        Thread.sleep(500);
        HWArticlePageObject.selectListByName("Learning programming");
        HWArticlePageObject.swipeElementToLeft("Java");
        Thread.sleep(500);
        HWArticlePageObject.clickOnElement("Java (programming language)");

        String titleBeforeDelete = "Java (programming language)";
        String titleAfterDelete = HWArticlePageObject.getTextAttribute();
        Assert.assertEquals(
                "Article title have been changed after delete article",
                titleBeforeDelete,
                titleAfterDelete
        );

    }
}
