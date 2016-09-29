package io.magentys.cinnamon.webdriver.collections;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

class ElementListCache {

    private final ElementLocator elementLocator;
    private List<WebElement> cachedElements;

    public ElementListCache(final ElementLocator locator, List<WebElement> elements) {
        this.elementLocator = locator;
        setElements(elements);
    }

    public synchronized List<WebElement> getElements() {
        if (cachedElements == null) {
            setElements(elementLocator.findElements());
        }
        invalidateCache();
        return cachedElements;
    }

    public synchronized void setElements(List<WebElement> cachedElements) {
        this.cachedElements = cachedElements;
    }

    private void invalidateCache() {
        List<WebElement> elements = elementLocator.findElements();
        if (elements.size() != cachedElements.size()) {
            setElements(elements);
        }
    }
}