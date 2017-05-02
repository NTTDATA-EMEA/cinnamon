package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.ByKey;
import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.KeyStrokeActions;
import io.magentys.cinnamon.webdriver.actions.PointActions;
import io.magentys.cinnamon.webdriver.actions.SelectAction;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.support.pagefactory.PageElementLocator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.collections.PageElementCollection.makeCollection;

public class PageElementImpl extends WebElementWrapper implements PageElement {

    public PageElementImpl(final WebElement element) {
        super(element);
    }

    public PageElementImpl(final WebDriver webDriver, final ElementLocator elementLocator, final WebElement element) {
        super(webDriver, elementLocator, element);
    }

    @Override
    public boolean isPresent() {
        return getWrappedElement() != null;
    }

    @Override
    public boolean is(final Condition<WebElement> condition) {
        return condition.apply(getWrappedElement());
    }

    public PageElement parent() {
        ElementLocator parentLocator = new PageElementLocator(elementLocator.getWebDriver(), By.xpath(".."));
        return PageElement.makeElement(parentLocator, parentLocator.findElement());
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.jmcore.webdriver.element.PageElement#element(java.lang.String)
     */
    @Override
    public PageElement element(final String locatorKey) {
        return element(ByKey.locatorKey(locatorKey));
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.jmcore.webdriver.element.PageElement#element(org.openqa.selenium.By)
     */
    @Override
    public PageElement element(final By by) {
        ElementLocator childLocator = new PageElementLocator(elementLocator.getWebDriver(), getWrappedElement(), by);
        return PageElement.makeElement(childLocator, childLocator.findElement());
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.jmcore.webdriver.element.PageElement#all(java.lang.String)
     */
    @Override
    public PageElementCollection all(final String locatorKey) {
        return all(ByKey.locatorKey(locatorKey));
    }

    /*
     * (non-Javadoc)
     * 
     * @see io.jmcore.webdriver.element.PageElement#all(org.openqa.selenium.By)
     */
    @Override
    public PageElementCollection all(final By by) {
        final ElementLocator childLocator = new PageElementLocator(elementLocator.getWebDriver(), getWrappedElement(), by);
        return makeCollection(childLocator, childLocator.findElements());
    }

    @Override
    public PageElement perform(Action action) {
        action.perform(getWrappedElement());
        return this;
    }

    @Override
    public PageElement waitUntil(final Condition<WebElement> condition) {
        return waitUntil(condition, defaultTimeout());
    }

    @Override
    public PageElement waitUntil(final Condition<WebElement> condition, Timeout timeout) {
        final ElementLocator filterLocator = new PageElementLocator(elementLocator.getWebDriver(), elementLocator.getSearchContext(),
                elementLocator.getBy(), elementLocator.getCondition().and(condition), timeout);
        return PageElement.makeElement(filterLocator, filterLocator.findElement());
    }

    @Override
    public void click() {
        actions.click(getWrappedElement());
    }

    @Override
    public PointActions byOffset(final int x, final int y) {
        return actions.byOffset(getWrappedElement(), x, y);
    }

    @Override
    public KeyStrokeActions withKeyStrokeInterval(final long intervalMillis) {
        return actions.withKeyStrokeInterval(getWrappedElement(), intervalMillis);
    }

    @Override
    public SelectAction select() {
        return actions.select(getWrappedElement());
    }

    @Override
    public PageElement deleteContent() {
        actions.deleteContent(getWrappedElement());
        return this;
    }

    @Override
    public PageElement replaceText(final CharSequence... keysToSend) {
        actions.replaceText(getWrappedElement(), keysToSend);
        return this;
    }

    @Override
    public PageElement typeText(final CharSequence... keysToSend) {
        actions.typeText(getWrappedElement(), keysToSend);
        return this;
    }

    @Override
    public PageElement fillIn(final CharSequence... keysToSend) {
        return typeText(keysToSend);
    }

    @Override
    public PageElement trimStartChars(int numChars){
        actions.trimStartChars(getWrappedElement(), numChars);
        return this;
    }

    @Override
    public PageElement trimEndChars(WebElement target, int numChar) {
        actions.trimEndChars(target, numChar);
        return this;
    }

    @Override
    public PageElement scrollIntoView() {
        actions.scrollIntoView(getWrappedElement());
        return this;
    }

    @Override
    public PageElement hoverOver() {
        actions.hoverOver(getWrappedElement());
        return this;
    }

    @Override
    public void doubleClick() {
        actions.doubleClick(getWrappedElement());
    }

    @Override
    public String text() {
        return getText();
    }
}