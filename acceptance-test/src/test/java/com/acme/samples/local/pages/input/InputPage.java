package com.acme.samples.local.pages.input;

import io.cucumber.datatable.DataTable;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.attributeEquals;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.inViewPort;

public class InputPage {

    @FindByKey("input.all")
    private PageElementCollection allElements;

    @FindByKey("input.sameLocator")
    private PageElementCollection sameLocators;

    public void deleteFrom(final String id) {
        element(id).deleteContent();
    }

    private PageElement element(final String id) {
        return allElements.first(attributeEquals("id", id));
    }

    public String getContent(final String id) {
        return element(id).text();
    }

    public void clearFrom(final String id) {
        element(id).clear();
    }

    public void clickOn(final String id) {
        element(id).click();
    }

    public void doubleClickOn(final String id) {
        element(id).doubleClick();
    }

    public void scrollTo(final String id) {
        element(id).scrollIntoView();
    }

    public boolean isElementVisibleInViewpoint(final String id) {
        return element(id).is(inViewPort);
    }

    public void sendKeysInto(final CharSequence chars, final String id) {
        element(id).typeText(chars);
    }

    public void typeTextInto(final List<CharSequence> text, final String id) {
        element(id).replaceText(text.toArray(new CharSequence[] {}));
    }

    public void typeTextInto(final String textToType, final String id, final int delayMillis) {
        element(id).withKeyStrokeInterval(delayMillis).replaceText(textToType);
    }

    public void typeTextIntoSameLocator(final String textToEnter, final String attribute, final String attributeValue) {
        sameLocators.first(attributeEquals(attribute, attributeValue)).replaceText(textToEnter);
    }

    public DataTable getDisplayedSameLocator() {
        final List<WebElement> elements = sameLocators.getWrappedElements();

        final List<List<String>> displayed = elements.stream()
                .map(element -> Arrays.asList("sameLocator", element.getAttribute("name"), element.getAttribute("value")))
                .collect(Collectors.toCollection(LinkedList::new));

        displayed.add(Arrays.asList("sameLocator", "name", "value"));

        return DataTable.create(displayed);
    }
}
