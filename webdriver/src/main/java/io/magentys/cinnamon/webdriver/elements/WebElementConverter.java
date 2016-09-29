package io.magentys.cinnamon.webdriver.elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.unwrapDriver;

public class WebElementConverter {

    public static WebElementConverter elementConverter() {
        return new WebElementConverter();
    }

    public List<String> getTextsFrom(final List<WebElement> elements) {
        return elements.stream().map(this::getTextFrom).collect(Collectors.toCollection(LinkedList::new));
    }

    public String getTextFrom(final WebElement element) {
        if (element.getTagName().equalsIgnoreCase("input") || element.getTagName().equalsIgnoreCase("textarea")) {
            return element.getAttribute("value");
        } else if (!element.isDisplayed() || !usesNativeArrayJoin(element)) {
            return getInnerText(element);
        }
        return element.getText();
    }

    private String getInnerText(final WebElement element) {
        return (String) ((JavascriptExecutor) unwrapDriver(element))
                .executeScript("if (typeof jQuery === 'undefined') { return arguments[0].innerText; } else { return $(arguments[0]).text(); }",
                        element);
    }

    private boolean usesNativeArrayJoin(final WebElement element) {
        return (Boolean) ((JavascriptExecutor) unwrapDriver(element))
                .executeScript("return (Array.prototype.join.toString().indexOf('[native code]') > -1)");
    }
}