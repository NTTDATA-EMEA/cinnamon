package io.magentys.cinnamon.webdriver.collections;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SizeGreaterThanCondition extends Condition<List<WebElement>> {

    private final int size;

    public SizeGreaterThanCondition(final int size) {
        this.size = size;
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        return elements.size() > size;
    }

    @Override
    public String toString() {
        return "size greater than: " + size;
    }
}