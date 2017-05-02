package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.KeyStrokeActions;
import io.magentys.cinnamon.webdriver.actions.PointActions;
import io.magentys.cinnamon.webdriver.actions.SelectAction;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;

import java.util.List;

/**
 * Facade covering all PageElement interfaces.
 * <p>
 * Uses composition over inheritance to provide separation of concerns between the different element types.
 * <p>
 * This class is required to support proxying of elements.
 */
public class PageElementFacade implements PageElement, TableElement {

    private final PageElement pageElement;
    private final Table tableElement;

    public PageElementFacade(final PageElement pageElement, final Table table) {
        this.pageElement = pageElement;
        this.tableElement = table;
    }

    @Override
    public void click() {
        pageElement.click();
    }

    @Override
    public void submit() {
        pageElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        pageElement.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        pageElement.clear();
    }

    @Override
    public String getTagName() {
        return pageElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return pageElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return pageElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return pageElement.isEnabled();
    }

    @Override
    public String getText() {
        return pageElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return pageElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return pageElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return pageElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return pageElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return pageElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return pageElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return pageElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return pageElement.getScreenshotAs(outputType);
    }

    @Override
    public WebElement getWrappedElement() {
        return pageElement.getWrappedElement();
    }

    @Override
    public Coordinates getCoordinates() {
        return pageElement.getCoordinates();
    }

    @Override
    public WebDriver getWrappedDriver() {
        return pageElement.getWrappedDriver();
    }

    @Override
    public boolean isPresent() {
        return pageElement.isPresent();
    }

    @Override
    public boolean is(final Condition<WebElement> condition) {
        return pageElement.is(condition);
    }

    @Override
    public PageElement parent() {
        return pageElement.parent();
    }

    @Override
    public <T extends PageElement> T element(final String locatorKey) {
        return pageElement.element(locatorKey);
    }

    @Override
    public <T extends PageElement> T element(final By by) {
        return pageElement.element(by);
    }

    @Override
    public PageElementCollection all(final String locatorKey) {
        return pageElement.all(locatorKey);
    }

    @Override
    public PageElementCollection all(final By by) {
        return pageElement.all(by);
    }

    @Override
    public PageElement perform(Action action) {
        return pageElement.perform(action);
    }

    @Override
    public PageElement waitUntil(final Condition<WebElement> condition) {
        return pageElement.waitUntil(condition);
    }

    @Override
    public PageElement waitUntil(final Condition<WebElement> condition, Timeout timeout) {
        return pageElement.waitUntil(condition, timeout);
    }

    @Override
    public PointActions byOffset(final int x, final int y) {
        return pageElement.byOffset(x, y);
    }

    @Override
    public KeyStrokeActions withKeyStrokeInterval(final long intervalMillis) {
        return pageElement.withKeyStrokeInterval(intervalMillis);
    }

    @Override
    public SelectAction select() {
        return pageElement.select();
    }

    @Override
    public PageElement deleteContent() {
        return pageElement.deleteContent();
    }

    @Override
    public PageElement replaceText(CharSequence... keysToSend) {
        return pageElement.replaceText(keysToSend);
    }

    @Override
    public PageElement fillIn(CharSequence... keysToSend) {
        return pageElement.fillIn(keysToSend);
    }

    @Override
    public PageElement typeText(CharSequence... keysToSend) {
        return pageElement.typeText(keysToSend);
    }

    @Override
    public PageElement trimEndChars(final WebElement target, int numChar) {return pageElement.trimEndChars(target, numChar);}

    @Override
    public PageElement scrollIntoView() {
        return pageElement.scrollIntoView();
    }

    @Override
    public PageElement hoverOver() {
        return pageElement.hoverOver();
    }

    @Override
    public void doubleClick() {
        pageElement.doubleClick();
    }

    @Override
    public String text() {
        return pageElement.text();
    }

    @Override
    public <T> List<T> asList(final Class<T> type) {
        return tableElement.asList(type);
    }

    @Override
    public <T> List<T> asList(final RowAdapter<T> adapter) {
        return tableElement.asList(adapter);
    }

    @Override
    public <T> List<T> asPivot(final CellAdapter<T> adapter) {
        return tableElement.asPivot(adapter);
    }

    @Override
    public <T> List<T> asPivot(final MultiCellAdapter<T> adapter) {
        return tableElement.asPivot(adapter);
    }

    @Override
    public Table withRowHeaderColspan(final int n) {
        return tableElement.withRowHeaderColspan(n);
    }

    @Override
    public MatchingCell firstMatch(final CellAdapter<Boolean> matcher) throws NoSuchElementException {
        return tableElement.firstMatch(matcher);
    }
}