package io.magentys.cinnamon.webdriver.elements;

import io.magentys.cinnamon.webdriver.actions.Actions;
import io.magentys.cinnamon.webdriver.actions.ActionsFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.Optional;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.unwrapDriver;
import static io.magentys.cinnamon.webdriver.elements.WebElementConverter.elementConverter;
import static org.openqa.selenium.support.pagefactory.ElementLocator.constructFrom;

public class WebElementWrapper implements WebElement, WrapsElement, Locatable, WrapsDriver {

    protected final ElementLocator elementLocator;
    protected final ElementCache cache;
    protected final Actions actions;

    public WebElementWrapper(final WebElement element) {
        this(unwrapDriver(element), constructFrom(unwrapDriver(element), element), element);
    }

    public WebElementWrapper(final WebDriver webDriver, final ElementLocator elementLocator, final WebElement element) {
        this.elementLocator = elementLocator;
        this.cache = new ElementCache(elementLocator, element);
        this.actions = ActionsFactory.create(webDriver);
    }

    @Override
    public WebElement getWrappedElement() {
        return Optional.ofNullable(cache.getElement()).orElseThrow(() -> new NoSuchElementException(
                String.format("Cannot find element using locator mechanism: %s and conditions: %s", elementLocator.getBy(),
                        elementLocator.getCondition())));
    }

    @Override
    public WebDriver getWrappedDriver() {
        return elementLocator.getWebDriver();
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) getWrappedElement()).getCoordinates();
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public void submit() {
        getWrappedElement().submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        getWrappedElement().sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public String getTagName() {
        return getWrappedElement().getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return getWrappedElement().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getWrappedElement().isEnabled();
    }

    @Override
    public String getText() {
        return elementConverter().getTextFrom(getWrappedElement());
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getWrappedElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getWrappedElement().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getWrappedElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getWrappedElement().getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return getWrappedElement().getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(outputType);
    }
}