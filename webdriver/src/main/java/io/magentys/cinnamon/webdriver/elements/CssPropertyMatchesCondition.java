package io.magentys.cinnamon.webdriver.elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class CssPropertyMatchesCondition extends Condition<WebElement> {

    private final String cssProperty;
    private final String regex;
    private final int flags;

    public CssPropertyMatchesCondition(final String cssProperty, final String regex) {
        this(cssProperty, regex, 0x00);
    }

    public CssPropertyMatchesCondition(final String cssProperty, final String regex, final int flags) {
        this.cssProperty = cssProperty;
        this.regex = regex;
        this.flags = flags;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            String cssValue = element.getCssValue(cssProperty);
            Pattern pattern = flags == 0x00 ? Pattern.compile(regex) : Pattern.compile(regex, flags);
            Matcher matcher = pattern.matcher(cssValue);
            return matcher.matches();
        } catch (NullPointerException | PatternSyntaxException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CssPropertyMatchesCondition))
            return false;

        CssPropertyMatchesCondition rhs = (CssPropertyMatchesCondition) o;
        return new EqualsBuilder().append(cssProperty, rhs.cssProperty).append(regex, rhs.regex).append(flags, rhs.flags).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cssProperty).append(regex).append(flags).toHashCode();
    }

    @Override
    public String toString() {
        return "css property: " + cssProperty + " matches: " + regex;
    }

}
