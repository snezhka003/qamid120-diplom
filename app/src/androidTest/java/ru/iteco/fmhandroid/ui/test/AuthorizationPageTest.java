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
import ru.iteco.fmhandroid.ui.pageObject.MainPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Authorization - страница авторизации")
public class AuthorizationPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authorizationPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    MainPage mainPage = new MainPage();
    private View decorView;

    @Before
    public void setUp() {
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            authorizationPage.verifySignInButtonVisible();
        } catch (Exception e) {
            navigationBar.clickOnProfileImage();
            navigationBar.clickOnLogout();
        }
    }

    @Test
    @DisplayName("Авторизация с валидными данными зарегистрированного пользователя")
    public void registeredUserAuthorization() throws InterruptedException {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        //Thread.sleep(1000);
        mainPage.theAllNewsItemIsDisplayed();
    }

    @Test
    @DisplayName("Авторизация с валидными данными незарегистрированного пользователя")
    public void unregisteredUserAuthorization() {
        authorizationPage.fillInTheAuthorizationFields(Data.UNREGISTER_LOGIN, Data.UNREGISTER_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с невалидным значением пробела в поле логина")
    public void authorizationWithSpaceInLogin() {
        authorizationPage.fillInTheAuthorizationFields(Data.FIELD_WITH_SPACE, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с невалидным значением пробела в поле пароля")
    public void authorizationWithSpaceInPassword() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.FIELD_WITH_SPACE);
        authorizationPage.clickOnSignIn();
        authorizationPage.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с пустым полем логина")
    public void authorizationWithEmptyLogin() {
        authorizationPage.fillInTheAuthorizationFields(Data.EMPTY_FIELD, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с пустым полем пароля")
    public void authorizationWithEmptyPassword() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.EMPTY_FIELD);
        authorizationPage.clickOnSignIn();
        authorizationPage.emptyFieldErrorMessageDisplay();
    }

    @Test
    @DisplayName("Авторизация с данными зарегистрированного пользователя, введенными символами разного регистра")
    public void authorizationWithDifferentCaseData() {
        authorizationPage.fillInTheAuthorizationFields(Data.INVALID_LOGIN, Data.INVALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поле логин")
    public void simpleSqlInjectionInLoginField() throws InterruptedException {
        authorizationPage.fillInTheAuthorizationFields(Data.SQL_INJECTION, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        //Thread.sleep(500);
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка SQL инъекции в поле пароль")
    public void simpleSqlInjectionInPasswordField() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.SQL_INJECTION);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поле логин")
    public void simpleXSSInjectionInLoginField() {
        authorizationPage.fillInTheAuthorizationFields(Data.XSS_INJECTION, Data.VALID_PASSWORD);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }

    @Test
    @DisplayName("Простая строка XSS инъекции в поле пароль")
    public void simpleXSSInjectionInPasswordField() {
        authorizationPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.XSS_INJECTION);
        authorizationPage.clickOnSignIn();
        authorizationPage.authorizationErrorMessageDisplay();
    }
}
