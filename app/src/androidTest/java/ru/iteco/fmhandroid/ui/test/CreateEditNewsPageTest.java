package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.generateRandomThreeDigitString;
import static ru.iteco.fmhandroid.ui.data.Helper.getCurrentTime;
import static ru.iteco.fmhandroid.ui.data.Helper.randomCategory;
import static ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage.STATUS_ACTIVE;
import static ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage.STATUS_NOT_ACTIVE;

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
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NavigationBar;
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage;
import ru.iteco.fmhandroid.ui.pageObject.CreateEditNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Creating News - страница создания новости, Editing News - страница редактирования новости")
public class CreateEditNewsPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    ControlPanelPage controlPanelPage = new ControlPanelPage();
    CreateEditNewsPage createEditNewsPage = new CreateEditNewsPage();
    String randomTitle = Data.NEWS_TITLE_TEXT + generateRandomThreeDigitString();

    @Before
    public void setUp() {
        try {
            authPage.verifySignInButtonVisible();
            // Кнопка авторизации видна → сразу логинимся и далее выполняем нужные действия
            authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
            authPage.clickOnSignIn();
        } catch (Exception e) {
            // Кнопки авторизации нет → проверяем текст-кнопку ALL NEWS, тем самым убеждаемся, что находимся на главной странице
            try {
                mainPage.verifyAllNewsButtonVisible();
                // Текст-кнопка ALL NEWS есть → сразу выполняем нужные действия
            } catch (Exception e2) {
                // Текст-кнопки ALL NEWS тоже нет → разлогиниваемся, логинимся заново и выполняем нужные действия
                navigationBar.clickOnProfileImage();
                navigationBar.clickOnLogout();
                authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
                authPage.clickOnSignIn();
            }
        }
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnNews();
        newsPage.openControlPanelPage();
    }

    // Creating News - страница создания новости
    @Test
    @DisplayName("Заполнение поочередно каждым значением из списка в поле Category на странице Creating News")
    public void shouldFillFieldOfCategoryByEachNewsCategoryInCreating() {
        controlPanelPage.openCreatingNewsPage();
        createEditNewsPage.verifySelectedCategories();
    }

    @Test
    @DisplayName("Создание новости с валидными значениями во всех полях формы")
    public void shouldCreateNews() {
        controlPanelPage.openCreatingNewsPage();
        createEditNewsPage.enterCategoryNews(randomCategory());
        createEditNewsPage.enterTitleNews(randomTitle);
        createEditNewsPage.setDate(0); // Опубликовать через указанное кол-во дней
        createEditNewsPage.setTime(getCurrentTime());
        createEditNewsPage.enterNewsDescription(Data.DESCRIPTION_TEXT);
        createEditNewsPage.clickOnSave();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkSearchResultIsDisplayed(randomTitle);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Неуспешное создание новости с пустыми полями формы, появление валидационного сообщения")
    public void shouldNotCreateNewsWithEmptyFields() {
        controlPanelPage.openCreatingNewsPage();
        createEditNewsPage.clickOnSave();
        createEditNewsPage.checkErrorMessage();
    }

    @Test
    @DisplayName("Закрытие диалогового окна без выхода со страницы создания новости при нажатии на кнопку CANCEL в окне")
    public void shouldCloseAlertDialogByCancelButton() {
        controlPanelPage.openCreatingNewsPage();
        createEditNewsPage.enterCategoryNews(Data.TRADE_UNION_CATEGORY);
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnCancelAlertDialog();
        createEditNewsPage.checkSearchResultIsDisplayed(Data.TRADE_UNION_CATEGORY);
    }

    @Test
    @DisplayName("Отмена создания новости и возврат на страницу Control panel при нажатии на кнопку OK в диалоговом окне")
    public void shouldCancelCreatingNews() {
        controlPanelPage.openCreatingNewsPage();
        createEditNewsPage.enterCategoryNews(randomCategory());
        createEditNewsPage.enterTitleNews(randomTitle);
        createEditNewsPage.setDate(0);
        createEditNewsPage.setTime(getCurrentTime());
        createEditNewsPage.enterNewsDescription(Data.DESCRIPTION_TEXT);
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnOkAlertDialog();
        controlPanelPage.showCreateNewsButton();
        createEditNewsPage.checkingTheResultOfNotCreatedNews(randomTitle);
    }

    // Editing News - страница редактирования новости
    @Test
    @DisplayName("Заполнение поочередно каждым значением из списка в поле Category на странице Editing News")
    public void shouldFillFieldOfCategoryByEachNewsCategoryInEditing() {
        createEditNewsPage.createNews(randomCategory(), randomTitle, 3, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.verifySelectedCategories();
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnOkAlertDialog();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Редактирование статуса активной новости переключением тогла Active в неактивное состояние")
    public void shouldEditNewsStatusToNotActive() {
        createEditNewsPage.createNews(Data.NEED_HELP_CATEGORY, randomTitle, 0, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.clickOnActivityToggle();
        createEditNewsPage.clickOnSave();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkStatusOfEditedNews(randomTitle, STATUS_NOT_ACTIVE);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Редактирование статуса неактивной новости переключением тогла Not active в активное состояние")
    public void shouldEditNewsStatusToActive() {
        createEditNewsPage.createNews(Data.HOLIDAY_CATEGORY, randomTitle, 1, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.clickOnActivityToggle();
        createEditNewsPage.clickOnSave();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.clickOnActivityToggle();
        createEditNewsPage.clickOnSave();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkStatusOfEditedNews(randomTitle, STATUS_ACTIVE);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Редактирование новости с валидными значениями во всех полях формы")
    public void shouldEditNews() {
        createEditNewsPage.createNews(Data.ANNOUNCEMENT_CATEGORY, randomTitle, 0, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.enterCategoryNews(Data.GRATITUDE_CATEGORY);
        createEditNewsPage.enterTitleNews(randomTitle + "editing");
        createEditNewsPage.setDate(1);
        createEditNewsPage.setTime(getCurrentTime());
        createEditNewsPage.enterNewsDescription(Data.DESCRIPTION_TEXT);
        createEditNewsPage.clickOnSave();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle + "editing");
        createEditNewsPage.checkSearchResultIsDisplayed(randomTitle + "editing");
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle + "editing");
        controlPanelPage.clickOnDeletingNews(randomTitle + "editing");
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Неуспешное редактирование новости с пустыми полями категории, описания, появление валидационного сообщения")
    public void shouldNotEditNewsWithEmptyFields() {
        createEditNewsPage.createNews(Data.MASSAGE_CATEGORY, randomTitle, 1, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.enterCategoryNews(Data.EMPTY_FIELD);
        createEditNewsPage.enterTitleNews(Data.EMPTY_FIELD);
        createEditNewsPage.enterNewsDescription(Data.EMPTY_FIELD);
        createEditNewsPage.clickOnSave();
        createEditNewsPage.checkErrorMessage();
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnOkAlertDialog();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Закрытие диалогового окна без выхода со страницы редактирования новости при нажатии на кнопку CANCEL в окне")
    public void shouldGoBackToEditingNewsByCancelButtonInAlertDialog() {
        createEditNewsPage.createNews(Data.SALARY_CATEGORY, randomTitle, 2, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.enterCategoryNews(Data.GRATITUDE_CATEGORY);
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnCancelAlertDialog();
        createEditNewsPage.checkSearchResultIsDisplayed(Data.GRATITUDE_CATEGORY);
        //удаление созданной для теста новости, чтобы не засорять систему
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnOkAlertDialog();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }

    @Test
    @DisplayName("Отмена редактирования новости и возврат на страницу Control panel при нажатии на кнопку OK в диалоговом окне")
    public void shouldCancelEditingNews() {
        createEditNewsPage.createNews(Data.NEED_HELP_CATEGORY, randomTitle, 1, getCurrentTime(), Data.DESCRIPTION_TEXT);
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.openNewsEditor(randomTitle);
        createEditNewsPage.enterCategoryNews(Data.GRATITUDE_CATEGORY);
        createEditNewsPage.enterTitleNews(randomTitle);
        createEditNewsPage.setDate(0);
        createEditNewsPage.setTime(getCurrentTime());
        createEditNewsPage.enterNewsDescription(Data.DESCRIPTION_TEXT);
        createEditNewsPage.clickOnCancel();
        createEditNewsPage.clickOnOkAlertDialog();
        controlPanelPage.showCreateNewsButton();
        createEditNewsPage.scrollingThroughTheNewsFeed(randomTitle);
        createEditNewsPage.checkSearchResultIsDisplayed(randomTitle);
        //удаление созданной для теста новости, чтобы не засорять систему
        controlPanelPage.clickOnDeletingNews(randomTitle);
        createEditNewsPage.clickOnOkAlertDialog();
    }
}
