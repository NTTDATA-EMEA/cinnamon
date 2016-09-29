package com.acme.samples.local.pages.mouse;

import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.attributeEquals;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.textContainsIgnoreCase;

public class MousePage {

    @FindByKey("mouse-page.content")
    private PageElement content;

    @FindByKey("mouse-page.elements")
    private PageElementCollection elements;

    public boolean containsText(final String expectedText) {
        return content.waitUntil(textContainsIgnoreCase(expectedText)) != null;
    }

    public void hoverOver(final String elementId) {
        element(elementId).hoverOver();
    }

    private PageElement element(final String elementId) {
        return elements.first(attributeEquals("id", elementId));
    }
}
