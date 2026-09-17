package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class NewsPage {

    public static ViewInteraction NEWS_MANAGEMENT_BUTTON = onView(withId(R.id.edit_news_material_button));

    public View decorView;

    public void showControlPanelButton() {
        Allure.step("Проверить кликабельность кнопки редактирования для перехода на страницу управления всеми новостями Control panel");
        NEWS_MANAGEMENT_BUTTON.check(matches(allOf(isDisplayed(), isClickable())));
    }

    public void openControlPanelPage() {
        Allure.step("Открыть страницу управления всеми новостями Control panel нажатием на кнопку редактирования");
        NEWS_MANAGEMENT_BUTTON.check(matches(isDisplayed())).perform(click());
    }
}
