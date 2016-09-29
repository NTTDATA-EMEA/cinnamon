package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class InnerHtmlContainsCondition extends Condition<WebElement> {

    private final boolean ignoreCase;
    private final String text;

    public InnerHtmlContainsCondition(final String text) {
        this(false, text);
    }

    public InnerHtmlContainsCondition(final boolean ignoreCase, final String text) {
        this.ignoreCase = ignoreCase;
        this.text = text;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            return ignoreCase ?
                    StringUtils.containsIgnoreCase(element.getAttribute("innerHTML"), text) :
                    element.getAttribute("innerHTML").contains(text);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof InnerHtmlContainsCondition))
            return false;

        InnerHtmlContainsCondition rhs = (InnerHtmlContainsCondition) o;
        return new EqualsBuilder().append(text, rhs.text).append(ignoreCase, rhs.ignoreCase).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(text).append(ignoreCase).toHashCode();
    }

    @Override
    public String toString() {
        return "inner HTML contains: " + text;
    }
}