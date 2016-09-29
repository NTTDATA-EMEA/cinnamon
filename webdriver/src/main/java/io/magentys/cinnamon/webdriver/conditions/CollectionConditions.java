package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.collections.SizeGreaterThanCondition;
import io.magentys.cinnamon.webdriver.collections.TextsContainCondition;
import org.openqa.selenium.WebElement;

import java.util.List;

public final class CollectionConditions {

    // Suppresses default constructor, ensuring non-instantiability.
    private CollectionConditions() {
    }

    public static Condition<List<WebElement>> sizeGreaterThan(final int size) {
        return new SizeGreaterThanCondition(size);
    }

    public static Condition<List<WebElement>> textsContain(final String... texts) {
        return new TextsContainCondition(texts);
    }

    public static Condition<List<WebElement>> textsContainIgnoreCase(final String... texts) {
        return new TextsContainCondition(true, texts);
    }
}