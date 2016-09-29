package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.Arrays;

import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;

public class TextContainsCondition extends Condition<WebElement> {

    private final WebElementConverter webElementConverter;
    private final boolean ignoreCase;
    private final String[] texts;

    public TextContainsCondition(final String... texts) {
        this(elementConverter(), false, texts);
    }

    public TextContainsCondition(final boolean ignoreCase, final String... texts) {
        this(elementConverter(), ignoreCase, texts);
    }

    TextContainsCondition(final WebElementConverter webElementConverter, final boolean ignoreCase, final String... texts) {
        this.webElementConverter = webElementConverter;
        this.ignoreCase = ignoreCase;
        this.texts = texts;
    }

    @Override
    public boolean apply(final WebElement element) {
        try {
            final String elementText = StringNormaliser.normalise(webElementConverter.getTextFrom(element));
            for (String text : texts) {
                if (ignoreCase ? !StringUtils.containsIgnoreCase(elementText, text) : !elementText.contains(text))
                    return false;
            }
            return true;
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TextContainsCondition))
            return false;

        TextContainsCondition rhs = (TextContainsCondition) o;
        return new EqualsBuilder().append(texts, rhs.texts).append(ignoreCase, rhs.ignoreCase).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(texts).append(ignoreCase).toHashCode();
    }

    @Override
    public String toString() {
        return "text contains: " + Arrays.asList(texts);
    }
}