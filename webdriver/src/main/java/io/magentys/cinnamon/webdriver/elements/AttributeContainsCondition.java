package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class AttributeContainsCondition extends Condition<WebElement> {

    private final String attribute;
    private final String value;

    public AttributeContainsCondition(final String attribute, final String value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            String attributeValue = element.getAttribute(attribute);
            return attributeValue != null && attributeValue.contains(value);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AttributeContainsCondition))
            return false;

        AttributeContainsCondition rhs = (AttributeContainsCondition) o;
        return new EqualsBuilder().append(attribute, rhs.attribute).append(value, rhs.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(attribute).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return "attribute: " + attribute + " contains: " + value;
    }

}
