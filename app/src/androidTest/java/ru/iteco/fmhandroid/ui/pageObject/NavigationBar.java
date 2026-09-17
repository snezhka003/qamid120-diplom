package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static ru.iteco.fmhandroid.ui.data.Helper.waitDisplayed;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;


public class NavigationBar {

    public static ViewInteraction BURGER_MENU_BUTTON = onView(withId(R.id.main_menu_image_button));
    public static ViewInteraction NEWS_BUTTON = onView(withText("News"));
    public static ViewInteraction MAIN_BUTTON = onView(withText("Main"));
    public static ViewInteraction ABOUT_BUTTON = onView(withText("About"));
    public static ViewInteraction LOVE_IS_ALL_BUTTON = onView(withId(R.id.our_mission_image_button));
    public static ViewInteraction PROFILE_IMAGE = onView(withId(R.id.authorization_image_button));
    public static ViewInteraction LOGOUT = onView(withText("Log out"));


    public void verifyBurgerMenuButtonVisible() {
        Allure.step("Проверить отображение кнопки бургер-меню в панели навигации");
        onView(isRoot()).perform(waitDisplayed(R.id.main_menu_image_button, 1000));
    }

    public void clickOnBurgerMenu() {
        Allure.step("Нажать на кнопку бургер-меню");
        BURGER_MENU_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnMain() {
        Allure.step("Выбрать раздел Main из списка разделов");
        MAIN_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnNews() {
        Allure.step("Выбрать раздел News из списка разделов");
        NEWS_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnAbout() {
        Allure.step("Выбрать раздел About из списка разделов");
        ABOUT_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void verifyLoveIsAllButtonVisible() {
        Allure.step("Проверить отображение иконки-кнопки Бабочка в панели навигации");
        onView(isRoot()).perform(waitDisplayed(R.id.main_menu_image_button, 1000));
    }

    public void openLoveIsAllPage() {
        Allure.step("Нажать на иконку-кнопку Бабочка в навигационной панели приложения");
        LOVE_IS_ALL_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void showLogout() {
        Allure.step("Проверить отображение кнопки выхода из учетной записи");
        LOGOUT.check(matches(isDisplayed()));
    }

    public void verifyProfileButtonVisible() {
        Allure.step("Проверка отображения иконки-кнопки Человек в панели навигации");
        onView(isRoot()).perform(waitDisplayed(R.id.authorization_image_button, 1000));
    }

    public void clickOnProfileImage() {
        Allure.step("Нажать на иконку-кнопку Человек в навигационной панели приложения");
        PROFILE_IMAGE.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnLogout() {
        Allure.step("Выход из учетной записи");
        LOGOUT.check(matches(isDisplayed())).perform(click());
    }
}
