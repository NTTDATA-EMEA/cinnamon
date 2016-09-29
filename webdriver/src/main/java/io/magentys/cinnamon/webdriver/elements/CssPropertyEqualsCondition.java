package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class CssPropertyEqualsCondition extends Condition<WebElement> {

    private final String cssProperty;
    private final String value;

    public CssPropertyEqualsCondition(final String cssProperty, final String value) {
        this.cssProperty = cssProperty;
        this.value = value;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            String cssValue = element.getCssValue(cssProperty);
            return cssValue != null && cssValue.equals(value);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CssPropertyEqualsCondition))
            return false;

        CssPropertyEqualsCondition rhs = (CssPropertyEqualsCondition) o;
        return new EqualsBuilder().append(cssProperty, rhs.cssProperty).append(value, rhs.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cssProperty).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return "css property: " + cssProperty + " equals: " + value;
    }

}
