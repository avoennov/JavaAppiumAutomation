package Exercises.HWtests;

import Exercises.HWlib.HWCoreTestCase;
import Exercises.HWlib.HWui.HWMainPageObject;
import Exercises.HWlib.HWui.HWSearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;


public class HWSearchTests extends HWCoreTestCase {

    private HWMainPageObject HWMainPageObject;
    protected void setUp() throws Exception {
        super.setUp();

        HWMainPageObject = new HWMainPageObject(driver);
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
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.searchElementHasText("Search Wikipedia");
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
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.initSearchInput();
        HWSearchPageObject.typeSearchLine("Java");
        HWSearchPageObject.waitForSearchResult("Island of Indonesia, Southeast Asia");
        HWSearchPageObject.waitForSearchResult("High-level programming language");
        HWSearchPageObject.waitForSearchResult("Object-oriented programming language");
        HWSearchPageObject.clickCancelSearch();
        HWSearchPageObject.waitForSearchResultDisappear("Object-oriented programming language");
    }

    /*
    Ex4*: Тест: проверка слов в поиске
    Написать тест, который делает поиск по какому-то слову. Например, JAVA. Затем убеждается, что в каждом результате поиска есть это слово.
    Внимание, прокручивать результаты выдачи поиска не надо. Это мы научимся делать на следующих занятиях. Пока надо работать только с теми
    результатами поиска, который видны сразу, без прокрутки.
    */

    @Test
    public void testCheckWordInSearchResults() {
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.initSearchInput();
        HWSearchPageObject.typeSearchLine("Java");
        HWSearchPageObject.checkEachSearchResultContainsText("Java");
    }

    /*
    Ex6: Тест: assert title
    Написать тест, который открывает статью и убеждается, что у нее есть элемент title. Важно: тест не должен дожидаться
    появления title, проверка должна производиться сразу. Если title не найден - тест падает с ошибкой.
    Метод можно назвать assertElementPresent.
    */

    @Test
    public void testAssertTitle() {
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.initSearchInput();
        HWSearchPageObject.typeSearchLine("Java");
        HWSearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        HWSearchPageObject.checkElementIsPresentNoWait();
    }

    /*
    Ex9*: Рефакторинг темплейта
    В одном из занятий четвертого урока упоминается о методе темплейтов. Там рассказано, как работать с локаторами, которые зависят от подстроки SUBSTRING.
    В примере из теста у нас всего одна подстрока. Но подобные локаторы можно строить с любым количеством подстрок.
    В приложении Wikipedia результатом поиска является набор ссылок на статьи, и каждая ссылка содержит как заголовок статьи, так и краткое описание.
    Например, для запроса “Java” одним из результатов выдачи будет “Java (Programming language)” и описание “Object-oriented programming language”.

    Задача:
    - Подобрать локатор, который находит результат поиска одновременно по заголовку и описанию (если заголовок или описание отличается - элемент не находится).
    - Добавить соответствующий метод в секцию TEMPLATES METHODS класса SearchPageObject.
    - В этот же класс добавить метод waitForElementByTitleAndDescription(String title, String description). Он должен дожидаться результата
        поиска по двум строкам - по заголовку и описанию. Если такой элемент не появляется, тест должен упасть с читаемой и понятной ошибкой.
    - Написать тест, который будет делать поиск по любому запросу на ваш выбор (поиск по этому слову должен возвращать как минимум 3 результата).
        Далее тест должен убеждаться, что первых три результата присутствуют в результате поиска.

    Результатом выполнения задания должен быть дифф к коду, который был написан на четвертом занятии. В этом диффе должны быть вспомогательные методы,
    лежащие в соответствующих классах и код теста, лежащего в соответствующем классе. Тест должен работать (т.е. проходить при верном результате поиска
    и обязательно падать, если результат поиска изменился).
    */

    @Test
    public void testCheckResultWithTitleAndDescription() {
        HWSearchPageObject HWSearchPageObject = new HWSearchPageObject(driver);
        HWSearchPageObject.initSearchInput();
        HWSearchPageObject.typeSearchLine("Java");
        HWSearchPageObject.waitForElementByTitleAndDescription("Java","Island of Indonesia, Southeast Asia");
        HWSearchPageObject.waitForElementByTitleAndDescription("JavaScript","High-level programming language");
        HWSearchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }

}
