package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.KeyStrokeActions;
import io.magentys.cinnamon.webdriver.actions.PointActions;
import io.magentys.cinnamon.webdriver.actions.SelectAction;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

public interface PageElement extends WebElement, WrapsElement, Locatable, WrapsDriver {

    boolean isPresent();

    boolean is(Condition<WebElement> condition);

    PageElement parent();

    /**
     * Find the first element matching the given locator key.
     * <p>
     * Specialisations of the PageElement interface can be used. Example: <code>
     * TableElement table = myElement.element("myTable");
     * </code>
     *
     * @param locatorKey The locator key
     * @param <T> A type that extends PageElement
     * @return The PageElement represented by the given locator key
     */
    <T extends PageElement> T element(String locatorKey);

    /**
     * Find the first element using the given method.
     *
     * @param by The locating mechanism
     * @param <T> A type that extends PageElement
     * @return The PageElement represented by the given method
     */
    <T extends PageElement> T element(By by);

    /**
     * Find all elements within the current context using the given locator key.
     *
     * @param locatorKey The locator key
     * @return The PageElementCollection represented by the given locator key
     */
    PageElementCollection all(String locatorKey);

    /**
     * Find all elements within the current context using the given method.
     *
     * @param by The locating mechanism
     * @return The PageElementCollection represented by the given method
     */
    PageElementCollection all(By by);

    PageElement perform(Action action);

    PageElement waitUntil(Condition<WebElement> condition);

    PageElement waitUntil(Condition<WebElement> condition, Timeout timeout);

    PointActions byOffset(int x, int y);

    KeyStrokeActions withKeyStrokeInterval(long intervalMillis);

    SelectAction select();

    PageElement deleteContent();

    PageElement replaceText(CharSequence... keysToSend);

    PageElement typeText(CharSequence... keysToSend);

    PageElement fillIn(CharSequence... keysToSend);

    PageElement scrollIntoView();

    PageElement hoverOver();

    void doubleClick();

    String text();

    static PageElement makeElement(final ElementLocator elementLocator, final WebElement element) {
        final PageElement pageElement = new PageElementImpl(elementLocator.getWebDriver(), elementLocator, element);
        final Table tableElement = new TableElementImpl(elementLocator, element);
        return new PageElementFacade(pageElement, tableElement);
    }
}