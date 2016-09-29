package io.magentys.cinnamon.webdriver.collections;

import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.pagefactory.PageElementLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.stream.Collectors;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.elements.PageElement.makeElement;
import static org.openqa.selenium.support.pagefactory.ElementLocator.constructFrom;

public class PageElementCollection implements WrapsElements {

    private final ElementLocator elementLocator;
    private final ElementListCache cache;

    public PageElementCollection(final ElementLocator elementLocator, final List<WebElement> elements) {
        this.elementLocator = elementLocator;
        this.cache = new ElementListCache(elementLocator, elements);
    }

    public static PageElementCollection makeCollection(final ElementLocator elementLocator, final List<WebElement> elements) {
        return new PageElementCollection(elementLocator, elements);
    }

    @Override
    public List<WebElement> getWrappedElements() {
        return cache.getElements();
    }

    public PageElementCollection waitUntil(Condition<List<WebElement>> condition) {
        return waitUntil(condition, defaultTimeout());
    }

    public PageElementCollection waitUntil(Condition<List<WebElement>> condition, Timeout timeout) {
        //TODO Enable collection condition to be applied during lookup.
        final long startTimeInMillis = System.currentTimeMillis();
        do {
            if (condition.apply(getWrappedElements())) {
                return this;
            }
        } while (System.currentTimeMillis() - startTimeInMillis < timeout.getMillis());
        throw new RuntimeException(String.format("Unable to match the condition: %s", condition));
    }

    public int size() {
        return getWrappedElements().size();
    }

    public PageElement first() {
        return asList().get(0);
    }

    public PageElement last() {
        return asList().get(size() - 1);
    }

    public PageElement first(final Condition<WebElement> condition) {
        return first(condition, defaultTimeout());
    }

    public PageElement first(final Condition<WebElement> condition, Timeout timeout) {
        return filter(condition, timeout).asList().get(0);
    }

    public PageElementCollection filter(final Condition<WebElement> condition) {
        return filter(condition, defaultTimeout());
    }

    public PageElementCollection filter(final Condition<WebElement> condition, Timeout timeout) {
        final ElementLocator filterLocator = new PageElementLocator(elementLocator.getWebDriver(), elementLocator.getSearchContext(),
                elementLocator.getBy(), elementLocator.getCondition().and(condition), timeout);
        return makeCollection(filterLocator, filterLocator.findElements());
    }

    public List<PageElement> asList() {
        return getWrappedElements().stream().map(element -> makeElement(
                constructFrom(elementLocator.getWebDriver(), element, elementLocator.getCondition(), elementLocator.getTimeout()), element))
                .collect(Collectors.toList());
    }
}