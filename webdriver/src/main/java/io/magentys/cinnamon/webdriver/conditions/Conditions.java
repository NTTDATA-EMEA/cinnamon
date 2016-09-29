package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.ReadyState;
import org.openqa.selenium.WebDriver;

public final class Conditions {

    // Suppresses default constructor, ensuring non-instantiability.
    private Conditions() {
    }

    public static <T> Condition<T> not(final Condition<T> condition) {
        return new NotCondition<>(condition);
    }

    public static Condition<WebDriver> ajaxFinished = new AjaxFinishedCondition();

    public static Condition<WebDriver> readyState(final ReadyState readyState) {
        return new DocumentReadyStateCondition(readyState);
    }

    public static <T> Condition<T> allOf(Condition<T>... conditions) {
        return new AndCondition<>(conditions);
    }

    public static <T> Condition<T> anyOf(Condition<T>... conditions) {
        return new OrCondition<>(conditions);
    }
}