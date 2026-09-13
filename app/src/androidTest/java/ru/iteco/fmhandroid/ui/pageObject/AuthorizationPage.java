package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Helper.waitDisplayed;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Data;


public class AuthorizationPage {
    public static ViewInteraction LOGIN_INPUT = onView((allOf(withHint("Login"), withParent(withParent(withId(R.id.login_text_input_layout))))));
    public static ViewInteraction PASSWORD_INPUT = onView(allOf(withHint("Password"), withParent(withParent(withId(R.id.password_text_input_layout)))));
    public static ViewInteraction SIGN_IN_BUTTON = onView(withId(R.id.enter_button));
    private View decorView;

    public void verifySignInButtonVisible() {
        Allure.step("Проверка отображения кнопки SIGN IN на странице авторизации");
        onView(isRoot()).perform(waitDisplayed(R.id.enter_button, 5000));
    }

    public void fillInTheAuthorizationFields(String loginText, String passwordText) {
        Allure.step("Ввести логин:" + loginText);
        LOGIN_INPUT.check(matches(isDisplayed())).perform(replaceText(loginText), closeSoftKeyboard());
        Allure.step("Ввести пароль:" + passwordText);
        PASSWORD_INPUT.check(matches(isDisplayed())).perform(replaceText(passwordText), closeSoftKeyboard());
    }

    public void clickOnSignIn() {
        Allure.step("Нажать кнопку SIGN IN");
        SIGN_IN_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void assertLoginField() {
        Allure.step("Проверка отображения поля логина");
        LOGIN_INPUT.check(matches(isDisplayed()));
    }

    public void authorizationErrorMessageDisplay() {
        Allure.step("Всплывающее сообщение об ошибке при авторизации");
        onView(withText(Data.AUTHENTICATION_ERROR_MESSAGE))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    public void emptyFieldErrorMessageDisplay() {
        Allure.step("Всплывающее сообщение о пустом поле при авторизации");
        onView(withText(Data.EMPTY_FIELDS_ERROR_MESSAGE))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }
}
