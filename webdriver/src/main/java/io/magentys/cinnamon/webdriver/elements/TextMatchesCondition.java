package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;

public class TextMatchesCondition extends Condition<WebElement> {

    private final WebElementConverter webElementConverter;
    private final int flags;
    private final String regex;

    public TextMatchesCondition(final String regex) {
        this(elementConverter(), 0x00, regex);
    }

    public TextMatchesCondition(final int flags, final String regex) {
        this(elementConverter(), flags, regex);
    }

    TextMatchesCondition(final WebElementConverter webElementConverter, final int flags, final String regex) {
        this.webElementConverter = webElementConverter;
        this.flags = flags;
        this.regex = regex;
    }

    @Override
    public boolean apply(WebElement element) {
        try {
            Pattern pattern = flags == 0x00 ? Pattern.compile(regex) : Pattern.compile(regex, flags);
            Matcher matcher = pattern.matcher(webElementConverter.getTextFrom(element));
            return matcher.matches();
        } catch (NullPointerException | PatternSyntaxException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TextMatchesCondition))
            return false;

        TextMatchesCondition rhs = (TextMatchesCondition) o;
        return new EqualsBuilder().append(regex, rhs.regex).append(flags, rhs.flags).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(regex).append(flags).toHashCode();
    }

    @Override
    public String toString() {
        return "text matches: " + regex;
    }
}