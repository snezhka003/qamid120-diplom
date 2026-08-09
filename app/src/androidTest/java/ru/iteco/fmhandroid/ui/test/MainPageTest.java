package ru.iteco.fmhandroid.ui.test;

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
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Раздел Main - главная страница")
public class MainPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();

    @Before
    public void setUp() {
        try {
            authPage.verifySignInButtonVisible();
        } catch (Exception e) {
            navigationBar.clickOnProfileImage();
            navigationBar.clickOnLogout();
        }
        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authPage.clickOnSignIn();
    }

    @Test
    @DisplayName("Сворачивание списка опубликованных новостей кнопкой в заголовке News")
    public void collapsingListOfActiveNews() {
        mainPage.expandMaterialButton();
        mainPage.allNewsItemNotDisplayed();
    }

    @Test
    @DisplayName("Разворачивание списка опубликованных новостей кнопкой в заголовке News")
    public void expandingListOfActiveNews() throws InterruptedException {
        Thread.sleep(1000);
        mainPage.expandMaterialButton();
        mainPage.allNewsItemNotDisplayed();
        mainPage.expandMaterialButton();
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Переход на страницу раздела \"News\" с главной страницы через кнопку \"ALL NEWS\"")
    public void shouldGoToNewsPageByButtonAllNews() {
        mainPage.clickOnAllNews();
        newsPage.showControlPanelButton();
    }
}
