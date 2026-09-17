package ru.iteco.fmhandroid.ui.test;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Epic;
import io.qameta.allure.kotlin.Issue;
import io.qameta.allure.kotlin.Link;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.pageObject.AboutPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.NavigationBar;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.LoveIsAllPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Переход между страницами основных разделов приложения")
public class NavigationBarTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    MainPage mainPage = new MainPage();
    NewsPage newsPage = new NewsPage();
    AboutPage aboutPage = new AboutPage();
    LoveIsAllPage loveIsAllPage = new LoveIsAllPage();

    @Before
    public void setUp() {
        try {
            authPage.verifySignInButtonVisible();
            // Кнопка авторизации видна → просто сразу логинимся и далее переходим к тесту
            authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
            authPage.clickOnSignIn();
        } catch (Exception e) {
            // Кнопки авторизации нет → проверяем текст-кнопку ALL NEWS, тем самым убеждаемся, что находимся на главной странице
            try {
                mainPage.verifyAllNewsButtonVisible();
                // Текст-кнопка ALL NEWS есть → сразу переходим к тесту
            } catch (Exception e2) {
                // Текст-кнопки ALL NEWS тоже нет → разлогиниваемся, логинимся заново и переходим к тесту
                navigationBar.clickOnProfileImage();
                navigationBar.clickOnLogout();
                authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
                authPage.clickOnSignIn();
            }
        }
    }

    @Test
    @DisplayName("Переход на страницу раздела \"News\" с главной страницы Main через бургер-меню")
    public void shouldGoToNewsPageFromMainPage() {
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnNews();
        newsPage.showControlPanelButton();
    }

    @Test
    @DisplayName("Переход на главную страницу Main со страницы раздела \"News\" через бургер-меню")
    public void shouldGoToMainPageFromNewsPage() {
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnNews();
        newsPage.showControlPanelButton();
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnMain();
        mainPage.verifyAllNewsButtonVisible();
    }

    @Test
    @DisplayName("Переход на страницу раздела \"About\" с главной страницы Main через бургер-меню")
    public void shouldGoToAboutPageFromMainPage() {
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnAbout();
        aboutPage.verifyBackButtonVisible();
    }

    @Test
    @DisplayName("Возврат на предыдущую страницу со страницы раздела \"About\" через кнопку Назад в навигационной панели приложения")
    public void shouldGoBackFromAboutPage() {
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnAbout();
        aboutPage.verifyBackButtonVisible();
        aboutPage.clickOnBack();
        mainPage.verifyAllNewsButtonVisible();
    }

    @Test // Тест не проходит! Заведен баг-репорт
    @DisplayName("Переход на страницу раздела \"About\" со страницы раздела News через бургер-меню")
    @Issue("1")
    @Link(name = "Ссылка на баг-репорт #1", url = "https://github.com/snezhka003/qamid120-diplom/issues/1")
    public void shouldGoToAboutPageFromNewsPage() {
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnNews();
        newsPage.showControlPanelButton();
        navigationBar.verifyBurgerMenuButtonVisible();
        navigationBar.clickOnBurgerMenu();
        navigationBar.clickOnAbout();
        aboutPage.verifyBackButtonVisible();
    }

    @Test
    @DisplayName("Переход на страницу раздела \"Love is all\" через кнопку в навигационной панели приложения")
    public void shouldGoToLoveIsAllPage() {
        navigationBar.verifyLoveIsAllButtonVisible();
        navigationBar.openLoveIsAllPage();
        loveIsAllPage.checkVisibilityTitleLoveIsAll();
    }

    @Test
    @DisplayName("Раскрытие доступного действия \"Log out\" в профиле через икноку-кнопку Человек в навигационной панели приложения ")
    public void shouldBeVisibleLogOutInProfile() {
        navigationBar.clickOnProfileImage();
        navigationBar.showLogout();
    }

    @Test
    @DisplayName("Выход из учетной записи при нажатии на \"Log out\" в профиле")
    public void shouldLogOutOfAccount() {
        navigationBar.verifyProfileButtonVisible();
        navigationBar.clickOnProfileImage();
        navigationBar.clickOnLogout();
        authPage.assertLoginField();
    }
}
