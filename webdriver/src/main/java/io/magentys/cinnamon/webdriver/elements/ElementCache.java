package io.magentys.cinnamon.webdriver.elements;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

class ElementCache {

    private final ElementLocator elementLocator;
    private WebElement cachedElement;

    public ElementCache(final ElementLocator locator, WebElement element) {
        this.elementLocator = locator;
        setElement(element);
    }

    public synchronized WebElement getElement() {
        if (cachedElement == null) {
            setElement(elementLocator.findElement());
        }
        invalidateCache();
        return cachedElement;
    }

    public synchronized void setElement(WebElement cachedElement) {
        this.cachedElement = cachedElement;
    }

    private void invalidateCache() {
        try {
            cachedElement.getTagName();
        } catch (NullPointerException | StaleElementReferenceException ex) {
            setElement(elementLocator.findElement());
        }
    }
}
