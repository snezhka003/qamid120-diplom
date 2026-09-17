package ru.iteco.fmhandroid.ui.test;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
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
import ru.iteco.fmhandroid.ui.pageObject.AboutPage;
import ru.iteco.fmhandroid.ui.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.NavigationBar;

@RunWith(AllureAndroidJUnit4.class)
@Epic("About - страница раздела с данными о приложении")
public class AboutPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    MainPage mainPage = new MainPage();
    AboutPage aboutPage = new AboutPage();

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
        navigationBar.clickOnAbout();
    }
//    public void setUp() {
//        try {
//            authPage.verifySignInButtonVisible();
//        } catch (Exception e) {
//            navigationBar.clickOnProfileImage();
//            navigationBar.clickOnLogout();
//        }
//        authPage.fillInTheAuthorizationFields(Data.VALID_LOGIN, Data.VALID_PASSWORD);
//        authPage.clickOnSignIn();
//        navigationBar.verifyBurgerMenuButtonVisible();
//        navigationBar.clickOnBurgerMenu();
//        navigationBar.clickOnAbout();
//    }

    @Test
    @DisplayName("Отображение заголовка подблока Version")
    public void displayTheApplicationVersionText() {
        aboutPage.appVersionTextApproval();
    }

    @Test
    @DisplayName("Отображение номера версии приложения в подблоке Version")
    public void displayTheApplicationVersionNumber() {
        aboutPage.appVersionNumberApproval();
    }

    @Test
    @DisplayName("Отображение заголовка подблока Privacy Policy")
    public void shouldDisplayPrivacyPolicyLabel() {
        aboutPage.assertDisplayOfPrivacyPolicyLabel();
    }

    @Test
    @DisplayName("Отображение заголовка подблока Terms of use")
    public void shouldDisplayTermsOfUseLabel() {
        aboutPage.assertDisplayOfTermsOfUseLabel();
    }

    @Test
    @DisplayName("Отображение наименования компании-разработчика")
    public void shouldDisplayTheCompanyNameLabel() {
        aboutPage.assertDisplayTheCompanyNameLabel();
    }

    @Test
    @DisplayName("Переход по верной ссылке с политикой конфиденциальности при нажатии на ссылку в подблоке \"Privacy Policy\"")
    public void thereMustBeCorrectPrivacyPolicyUrl() {
        Intents.init();
        // Говорим: если приложение попытается запустить браузер — ничего не делать
        Intents.intending(IntentMatchers.anyIntent())
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null));
        aboutPage.clickOnPrivacyPolicy();
        aboutPage.verifyIntent(Data.PRIVACY_POLICY_URL);
        Intents.release();
    }

    @Test
    @DisplayName("Переход по верной ссылке с пользовательским соглашением при нажатии на ссылку в подблоке \"Terms of use\"")
    public void thereMustBeCorrectTermsOfUseUrl() {
        Intents.init();
        // Говорим: если приложение попытается запустить браузер — ничего не делать
        Intents.intending(IntentMatchers.anyIntent())
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null));
        aboutPage.clickOnTermsOfUse();
        aboutPage.verifyIntent(Data.TERMS_OF_USE_URL);
        Intents.release();
    }
}
