package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;


public class MainPage {

    public static ViewInteraction ALL_NEWS_TEXT_VIEW = onView(withId(R.id.all_news_text_view));
    public static ViewInteraction EXPAND_MATERIAL_BUTTON = onView(withId(R.id.expand_material_button));


    public void expandMaterialButton() {
        Allure.step("Аккордеон на главной странице Main");
        EXPAND_MATERIAL_BUTTON.check(matches(isDisplayed())).perform(click());
    }

    public void allNewsItemNotDisplayed() {
        Allure.step("Проверка отсутствия элемента на главной странице Main");
        ALL_NEWS_TEXT_VIEW.check(matches(not(isDisplayed())));
    }

    public void theAllNewsItemIsDisplayed() {
        Allure.step("Проверка наличия элемента на главной странице Main");
        ALL_NEWS_TEXT_VIEW.check(matches(isDisplayed()));
    }

    public void clickOnAllNews() {
        Allure.step("Нажать на кнопку All News");
        ALL_NEWS_TEXT_VIEW.perform(click());
    }
}
