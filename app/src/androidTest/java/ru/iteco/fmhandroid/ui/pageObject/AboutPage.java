package ru.iteco.fmhandroid.ui.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Helper.waitDisplayed;

import android.content.Intent;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;

public class AboutPage {
    public static ViewInteraction BACK_BUTTON_FROM_ABOUT_PAGE = onView(withId(R.id.about_back_image_button));
    public static ViewInteraction VERSION_TEXT = onView(withId(R.id.about_version_title_text_view));
    public static ViewInteraction VERSION_NUMBER = onView(withId(R.id.about_version_value_text_view));
    public static ViewInteraction LABEL_PRIVACY_POLICY = onView(withId(R.id.about_privacy_policy_label_text_view));
    public static ViewInteraction LABEL_TERMS_OF_USE = onView(withId(R.id.about_terms_of_use_label_text_view));
    public static ViewInteraction LABEL_COMPANY_TEXT = onView(withId(R.id.about_company_info_label_text_view));
    public static ViewInteraction PRIVACY_POLICY_BUTTON_LINK = onView(withId(R.id.about_privacy_policy_value_text_view));
    public static ViewInteraction TERMS_OF_USE_BUTTON_LINK = onView(withId(R.id.about_terms_of_use_value_text_view));

    public void verifyBackButtonVisible() {
        Allure.step("Проверка отображения кнопки Назад в навигационной панели приложения");
        onView(isRoot()).perform(waitDisplayed(R.id.about_back_image_button, 1000));
    }

    public void appVersionTextApproval() {
        Allure.step("Отображение заголовка подблока Version");
        VERSION_TEXT.check(matches(isDisplayed()));
    }

    public void appVersionNumberApproval() {
        Allure.step("Отображение номера версии приложения");
        VERSION_NUMBER.check(matches(isDisplayed()));
    }

    public void assertDisplayOfPrivacyPolicyLabel() {
        Allure.step("Отображение заголовка подблока Privacy Policy");
        LABEL_PRIVACY_POLICY.check(matches(isDisplayed()));
    }

    public void assertDisplayOfTermsOfUseLabel() {
        Allure.step("Отображение заголовка подблока Terms of use");
        LABEL_TERMS_OF_USE.check(matches(isDisplayed()));
    }

    public void assertDisplayTheCompanyNameLabel() {
        Allure.step("Отображение наименования компании-разработчика");
        LABEL_COMPANY_TEXT.check(matches(isDisplayed()));
    }

    public void clickOnBack() {
        Allure.step("Нажать кнопку Назад в навигационной панели приложения");
        BACK_BUTTON_FROM_ABOUT_PAGE.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnPrivacyPolicy() {
        Allure.step("Клик на ссылку Политика конфиденциальности");
        PRIVACY_POLICY_BUTTON_LINK.check(matches(isDisplayed())).perform(click());
    }

    public void clickOnTermsOfUse() {
        Allure.step("Клик на ссылку Условия эксплуатации");
        TERMS_OF_USE_BUTTON_LINK.check(matches(isDisplayed())).perform(click());
    }

    public void verifyIntent(String expectedUrl) {
        Allure.step("Проверка, что инициирован Intent с действием VIEW и верным URL");
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(expectedUrl)
        ));
    }
}
