package io.magentys.cinnamon.webdriver.elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class AttributeMatchesCondition extends Condition<WebElement> {

    private final String attribute;
    private final String regex;
    private final int flags;

    public AttributeMatchesCondition(final String attribute, final String regex) {
        this(attribute, regex, 0x00);
    }

    public AttributeMatchesCondition(final String attribute, final String regex, final int flags) {
        this.attribute = attribute;
        this.regex = regex;
        this.flags = flags;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            String attributeValue = element.getAttribute(attribute);
            Pattern pattern = flags == 0x00 ? Pattern.compile(regex) : Pattern.compile(regex, flags);
            Matcher matcher = pattern.matcher(attributeValue);
            return matcher.matches();
        } catch (NullPointerException | PatternSyntaxException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AttributeMatchesCondition))
            return false;

        AttributeMatchesCondition rhs = (AttributeMatchesCondition) o;
        return new EqualsBuilder().append(attribute, rhs.attribute).append(regex, rhs.regex).append(flags, rhs.flags).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(attribute).append(regex).append(flags).toHashCode();
    }

    @Override
    public String toString() {
        return "attribute: " + attribute + " matches: " + regex;
    }

}
