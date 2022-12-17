package Exercises.HWtests;

import Exercises.HWlib.HWCoreTestCase;
import Exercises.HWlib.HWui.HWArticlePageObject;
import Exercises.HWlib.HWui.HWSearchPageObject;
import org.junit.Test;

public class HWChangeAppConditionTests extends HWCoreTestCase {

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
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.initSearchInput();
        HWSearchPageObject.typeSearchLine("Java");
        HWSearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        HWArticlePageObject HWArticlePageObject = new HWArticlePageObject(driver);
        String title_before_rotation = HWArticlePageObject.getTextAttribute();
        this.rotateScreenLandscape();
        String title_after_rotation = HWArticlePageObject.getTextAttribute();

        assertEquals(
                "Article title have been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

    }
}
