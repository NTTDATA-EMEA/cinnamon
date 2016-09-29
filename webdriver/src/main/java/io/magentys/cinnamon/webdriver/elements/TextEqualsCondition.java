package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;

public class TextEqualsCondition extends Condition<WebElement> {

    private final WebElementConverter webElementConverter;
    private final boolean ignoreCase;
    private final String text;

    public TextEqualsCondition(final String text) {
        this(elementConverter(), false, text);
    }

    public TextEqualsCondition(final boolean ignoreCase, final String text) {
        this(elementConverter(), ignoreCase, text);
    }

    TextEqualsCondition(final WebElementConverter webElementConverter, final boolean ignoreCase, final String text) {
        this.webElementConverter = webElementConverter;
        this.ignoreCase = ignoreCase;
        this.text = text;
    }

    @Override
    public boolean apply(WebElement element) {
        try {
            final String elementText = StringNormaliser.normalise(webElementConverter.getTextFrom(element));
            return ignoreCase ? elementText.equalsIgnoreCase(text) : elementText.equals(text);
        } catch (NullPointerException | WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TextEqualsCondition))
            return false;

        TextEqualsCondition rhs = (TextEqualsCondition) o;
        return new EqualsBuilder().append(text, rhs.text).append(ignoreCase, rhs.ignoreCase).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(text).append(ignoreCase).toHashCode();
    }

    @Override
    public String toString() {
        return "text equals: " + text;
    }
}