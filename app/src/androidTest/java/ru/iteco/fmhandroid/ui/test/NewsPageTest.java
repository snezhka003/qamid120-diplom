package ru.iteco.fmhandroid.ui.test;

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
import ru.iteco.fmhandroid.ui.pageObject.ControlPanelPage;
import ru.iteco.fmhandroid.ui.pageObject.CreateEditNewsPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Раздел News - страница с опубликованными новостями")
public class NewsPageTest {

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
    }


    @Test
    @DisplayName("Переход на страницу настройки фильтра Filter news через кнопку фильтрации в панели инструментов страницы")
    public void shouldGoToFilterNewsPage() {
        controlPanelPage.openNewsFilter();
    }

    @Test
    @DisplayName("Переход на страницу раздела \"News\" со страницы настройки фильтра Filter news через кнопку \"CANCEL\"")
    public void shouldGoToNewsPageFromFilterNewsPageByCancel() {
        controlPanelPage.openNewsFilter();
        createEditNewsPage.pressCancel();
        newsPage.showControlPanelButton();
    }

    @Test
    @DisplayName("Переход на страницу управления всеми новостями Control panel через кнопку редактирования в панели инструментов страницы")
    public void shouldGoToControlPanelPage() throws InterruptedException {
        Thread.sleep(1000);
        newsPage.openControlPanelPage();
        controlPanelPage.showCreateNewsButton();
    }
}
