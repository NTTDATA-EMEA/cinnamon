package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;

public class EmptyCondition extends Condition<WebElement> {

    private final WebElementConverter webElementConverter;

    public EmptyCondition() {
        this(elementConverter());
    }

    EmptyCondition(final WebElementConverter webElementConverter) {
        this.webElementConverter = webElementConverter;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            return webElementConverter.getTextFrom(element).isEmpty();
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        return o == this || o instanceof EmptyCondition;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().toHashCode();
    }

    @Override
    public String toString() {
        return "empty";
    }
}