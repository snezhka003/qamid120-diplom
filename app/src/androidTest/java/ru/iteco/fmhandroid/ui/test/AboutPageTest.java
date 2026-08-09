package ru.iteco.fmhandroid.ui.test;

import androidx.test.espresso.intent.Intents;
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
import ru.iteco.fmhandroid.ui.pageObject.NavigationBar;

@RunWith(AllureAndroidJUnit4.class)
@Epic("About - страница раздела с данными о приложении")
public class AboutPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    AboutPage aboutPage = new AboutPage();

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
        navigationBar.clickOnAbout();
    }

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
        aboutPage.clickOnPrivacyPolicy();
        aboutPage.verifyIntent(Data.PRIVACY_POLICY_URL);
        Intents.release();
    }

    @Test
    @DisplayName("Переход по верной ссылке с пользовательским соглашением при нажатии на ссылку в подблоке \"Terms of use\"")
    public void thereMustBeCorrectTermsOfUseUrl() {
        Intents.init();
        aboutPage.clickOnTermsOfUse();
        aboutPage.verifyIntent(Data.TERMS_OF_USE_URL);
        Intents.release();
    }
}
