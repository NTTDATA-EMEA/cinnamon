package io.magentys.cinnamon.webdriver.conditions;

import io.magentys.cinnamon.webdriver.actions.synthetic.DomEvent;
import io.magentys.cinnamon.webdriver.elements.*;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.conditions.Conditions.not;

public final class ElementConditions {

    // Suppresses default constructor, ensuring non-instantiability.
    private ElementConditions() {
    }

    public static Condition<WebElement> displayed = new DisplayedCondition();

    public static Condition<WebElement> hidden = not(displayed);

    public static Condition<WebElement> empty = new EmptyCondition();

    public static Condition<WebElement> notEmpty = not(empty);

    public static Condition<WebElement> enabled = new EnabledCondition();

    public static Condition<WebElement> disabled = not(enabled);

    public static Condition<WebElement> inViewPort = new InViewPortCondition();

    public static Condition<WebElement> obscured = new ObscuredCondition();

    public static Condition<WebElement> notObscured = not(obscured);

    public static Condition<WebElement> selected = new SelectedCondition();

    public static Condition<WebElement> deselected = not(selected);

    public static Condition<WebElement> clickable = new AndCondition<>(displayed, enabled, notObscured);

    public static Condition<WebElement> present = new Condition<WebElement>() {

        @Override
        public boolean apply(final WebElement element) {
            return null != element;
        }

        @Override
        public String toString() {
            return "present";
        }

    };

    public static Condition<WebElement> attributeContains(final String attribute, final String value) {
        return new AttributeContainsCondition(attribute, value);
    }

    public static Condition<WebElement> attributeEquals(final String attribute, final String value) {
        return new AttributeEqualsCondition(attribute, value);
    }

    public static Condition<WebElement> attributeMatches(final String attribute, final String regex) {
        return new AttributeMatchesCondition(attribute, regex);
    }

    public static Condition<WebElement> attributeMatches(final String attribute, final String regex, final int flags) {
        return new AttributeMatchesCondition(attribute, regex, flags);
    }

    public static Condition<WebElement> cssPropertyContains(final String cssProperty, final String value) {
        return new CssPropertyContainsCondition(cssProperty, value);
    }

    public static Condition<WebElement> cssPropertyEquals(final String cssProperty, final String value) {
        return new CssPropertyEqualsCondition(cssProperty, value);
    }

    public static Condition<WebElement> cssPropertyMatches(final String cssProperty, final String regex) {
        return new CssPropertyMatchesCondition(cssProperty, regex);
    }

    public static Condition<WebElement> cssPropertyMatches(final String cssProperty, final String regex, final int flags) {
        return new CssPropertyMatchesCondition(cssProperty, regex, flags);
    }

    public static Condition<WebElement> eventListenerRegistered(final DomEvent event) {
        return new EventListenerRegisteredCondition(event);
    }

    public static Condition<WebElement> innerHtmlContains(final String text) {
        return new InnerHtmlContainsCondition(text);
    }

    public static Condition<WebElement> innerHtmlContainsIgnoreCase(final String text) {
        return new InnerHtmlContainsCondition(true, text);
    }

    public static Condition<WebElement> positionUnchanged(final long delayIntervalMillis) {
        return new PositionUnchangedCondition(delayIntervalMillis);
    }

    public static Condition<WebElement> textContains(final String... text) {
        return new TextContainsCondition(text);
    }

    public static Condition<WebElement> textContainsIgnoreCase(final String... text) {
        return new TextContainsCondition(true, text);
    }

    public static Condition<WebElement> textEquals(final String text) {
        return new TextEqualsCondition(text);
    }

    public static Condition<WebElement> textEqualsIgnoreCase(final String text, final boolean ignoreCase) {
        return new TextEqualsCondition(true, text);
    }

    public static Condition<WebElement> textMatches(final String text) {
        return new TextMatchesCondition(text);
    }

    public static Condition<WebElement> textMatches(final String text, final int flags) {
        return new TextMatchesCondition(flags, text);
    }
}