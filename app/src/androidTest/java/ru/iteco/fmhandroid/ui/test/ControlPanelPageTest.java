package ru.iteco.fmhandroid.ui.test;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.generateRandomThreeDigitString;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentTime;
import static ru.iteco.fmhandroid.ui.data.Helper.randomCategory;
import static ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage.STATUS_ACTIVE;
import static ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage.STATUS_NOT_ACTIVE;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.NavigationBar;
import ru.iteco.fmhandroid.ui.pageObject.CreateEditNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Control panel - страница управления всеми новостями")
public class ControlPanelPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    NewsPage newsPage = new NewsPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreateEditNewsPage createEditNewsPage = new CreateEditNewsPage();

    private View decorView;

    @Before
    public void setUp() throws InterruptedException {
        try {
            authPage.verifySignInButtonVisible();
        } catch (Exception e) {
            navigationBar.clickOnProfileImage();
            navigationBar.clickOnLogout();
        }
        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authPage.clickOnSignIn();
        Thread.sleep(1000);
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnNews();
        newsPage.openControlPanelPage();
    }

    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();

    @Test
    @DisplayName("Переход на страницу настройки фильтра Filter news через кнопку фильтрации в панели инструментов страницы")
    public void shouldGoToFilterNewsPage() {
        controlPanelPage.openNewsFilter();
    }

    @Test
    @DisplayName("Переход на страницу Control panel со страницы настройки фильтра Filter news через кнопку \"CANCEL\"")
    public void shouldGoToControlPanelPageFromFilterNewsPageByCancel() {
        controlPanelPage.openNewsFilter();
        createEditNewsPage.pressCancel();
        controlPanelPage.showCreateNewsButton();
    }

    @Test
    @DisplayName("Переход на страницу создания новости Creating News через кнопку создания новости в панели инструментов страницы")
    public void shouldGoToCreatingNewsPage() {
        controlPanelPage.openCreatingNewsPage();
        controlPanelPage.verifyTextOnCreatingOrEditingNewsPage(Data.CREATING_PAGE_TEXT);
    }

    @Test
    @DisplayName("Переход на страницу страницу редактирования новости Editing News через кнопку редактирования на плашке новости")
    public void shouldGoToEditingNewsPage() {
        createEditNewsPage.createNews(randomCategory(), randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        controlPanelPage.verifyTextOnCreatingOrEditingNewsPage(Data.EDITING_PAGE_TEXT);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.pressCancel();
        createEditNewsPage.pressOkAlertDialog();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressOkAlertDialog();
    }

    @Test
    @DisplayName("Сортировка списка всех новостей по возрастанию даты публикации")
    public void verifyNewsDateSortingAsc() {
        String firstDateBeforeSorting = controlPanelPage.getFirstNewsDate();
        String lastDateBeforeSorting = controlPanelPage.getLastNewsDate();
        controlPanelPage.clickOnSortingNews();
        String firstDateAfterSorting = controlPanelPage.getFirstNewsDate();
        String lastDateAfterSorting = controlPanelPage.getLastNewsDate();
        assertEquals(lastDateBeforeSorting, firstDateAfterSorting);
        assertEquals(firstDateBeforeSorting, lastDateAfterSorting);
    }

    @Test
    @DisplayName("Сортировка списка всех новостей по убыванию даты публикации")
    public void verifyNewsDateSortingDesc() {
        String firstDateBeforeSorting = controlPanelPage.getFirstNewsDate();
        String lastDateBeforeSorting = controlPanelPage.getLastNewsDate();
        controlPanelPage.clickOnSortingNews();
        controlPanelPage.clickOnSortingNews();
        String firstDateAfterSorting = controlPanelPage.getFirstNewsDate();
        String lastDateAfterSorting = controlPanelPage.getLastNewsDate();
        assertEquals(firstDateBeforeSorting, firstDateAfterSorting);
        assertEquals(lastDateBeforeSorting, lastDateAfterSorting);
    }

    @Test
    @DisplayName("Фильтрация списка всех новостей по периоду времени, без категории, с активированными обоими чек-боксами по умолчанию")
    public void shouldFilterNewsByDateRange() {
        controlPanelPage.openNewsFilter();
        controlPanelPage.enterFromWhatDate(-14); // Дней назад
        controlPanelPage.enterUntilWhatDate(14); // Дней вперед
        controlPanelPage.clickOnApplyFilterButton();
        controlPanelPage.checkAllNewsDateRange(-14, 14);
    }

    @Test
    @DisplayName("Фильтрация списка всех новостей по категории + период времени + активирован чек-бокс Active")
    public void shouldSearchForNewsViaFilterForCurrentDate() {
        createEditNewsPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 30, "14:32", Data.DESCRIPTION_TEXT);
        controlPanelPage.openNewsFilter();
        createEditNewsPage.enterCategoryNews(Data.MASSAGE_CATEGORY);
        controlPanelPage.enterFromWhatDate(30);
        controlPanelPage.enterUntilWhatDate(30);
        controlPanelPage.clickOnCheckBoxNotActive();
        controlPanelPage.clickOnApplyFilterButton();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkSearchResultIsDisplayed(randomTitle);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressOkAlertDialog();
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'ACTIVE'")
    public void checkAllNewsAreActive() {
        controlPanelPage.openNewsFilter();
        controlPanelPage.clickOnCheckBoxNotActive();
        controlPanelPage.clickOnApplyFilterButton();
        controlPanelPage.checkAllNewsStatus(STATUS_ACTIVE);
    }

    @Test
    @DisplayName("Фильтрация всех новостей по статусу 'NOT ACTIVE'")
    public void checkAllNewsAreNotActive() {
        controlPanelPage.openNewsFilter();
        controlPanelPage.clickOnCheckBoxActive();
        controlPanelPage.clickOnApplyFilterButton();
        controlPanelPage.checkAllNewsStatus(STATUS_NOT_ACTIVE);
    }

    @Test
    @DisplayName("Фильтрация списка всех новостей с активированным чек-боксом Not active, без категории, без периода времени")
    public void shouldFilterNewsByStatusNotActive() {
        createEditNewsPage.theStatusOfTheEditedNewsIsNotActive(randomTitle);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.openNewsFilter();
        controlPanelPage.clickOnCheckBoxActive();
        controlPanelPage.clickOnApplyFilterButton();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkStatusOfEditedNews(randomTitle, STATUS_NOT_ACTIVE);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressOkAlertDialog();
    }

    @Test
    @DisplayName("Поиск через фильтр новости со статусом ACTIVE")
    public void shouldFilterNewsByStatusActive() {
        createEditNewsPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 0, "20:00", Data.DESCRIPTION_TEXT);
        controlPanelPage.openNewsFilter();
        controlPanelPage.clickOnCheckBoxNotActive();
        controlPanelPage.clickOnApplyFilterButton();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkStatusOfEditedNews(randomTitle, STATUS_ACTIVE);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressOkAlertDialog();
    }

    @Test
    @DisplayName("Возврат на страницу Control panel без применения фильтрации при нажатии на кнопку \"CANCEL\" с заполненными полями фильтра")
    public void shouldAllowToCancelNewsFilterApplication() {
        controlPanelPage.openNewsFilter();
        createEditNewsPage.enterCategoryNews(Data.BIRTHDAY_CATEGORY);
        controlPanelPage.enterFromWhatDate(30);
        controlPanelPage.enterUntilWhatDate(30);
        controlPanelPage.clickOnCheckBoxActive();
        controlPanelPage.clickOnCheckBoxNotActive();
        createEditNewsPage.pressCancel();
        controlPanelPage.showCreateNewsButton();
    }

    @Test
    @DisplayName("Закрытие диалогового окна без удаления новости при нажатии на кнопку CANCEL в окне")
    public void shouldCancelDeletionOfNews() {
        createEditNewsPage.createNews(randomCategory(), randomTitle, 10, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressCancelAlertDialog();
        controlPanelPage.showCreateNewsButton();
        controlPanelPage.checkingTheResultOfNotDeletingNews(randomTitle);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressOkAlertDialog();
    }

    @Test
    @DisplayName("Удаление созданной новости")
    public void deletingCreatedNews() {
        createEditNewsPage.createNews(Data.GRATITUDE_CATEGORY, randomTitle, 7, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.pressOkAlertDialog();
        controlPanelPage.checkingTheResultOfDeletingNews(randomTitle);
    }
}
