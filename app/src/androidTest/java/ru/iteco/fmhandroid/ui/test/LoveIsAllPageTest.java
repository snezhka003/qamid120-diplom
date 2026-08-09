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
import ru.iteco.fmhandroid.ui.pageObject.LoveIsAllPage;


@RunWith(AllureAndroidJUnit4.class)
@Epic("Love is all - страница с цитатами")
public class LoveIsAllPageTest {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    AuthorizationPage authPage = new AuthorizationPage();
    NavigationBar navigationBar = new NavigationBar();
    LoveIsAllPage loveIsAllPage = new LoveIsAllPage();

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
        Thread.sleep(500);
        navigationBar.openLoveIsAllPage();
    }

    @Test
    @DisplayName("Отображение заголовков цитат")
    public void verifyQuoteTitle() {
        loveIsAllPage.assertDisplayOfAllQuoteTitles();
    }

    @Test
    @DisplayName("Разворачивание контента цитаты нажатием на плашку первой цитаты в списке")
    public void shouldDisplayDescriptionOfQuotes1() {
        loveIsAllPage.expandQuoteByPosition(0);
        loveIsAllPage.scrollToQuotePosition(0);
        loveIsAllPage.checkQuoteDescription(Data.QUOTE_1_DESCRIPTION);
    }
}
