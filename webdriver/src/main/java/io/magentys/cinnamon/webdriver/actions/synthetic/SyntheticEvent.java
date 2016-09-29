package io.magentys.cinnamon.webdriver.actions.synthetic;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;

/**
 * Simulates the JavaScript events directly, instead of letting the browser
 * generate them.
 */
public class SyntheticEvent {

    private final JavascriptExecutor js;

    public SyntheticEvent(WebDriver webDriver) {
        this.js = (JavascriptExecutor) webDriver;
    }

    public void executeScript(String script) {
        js.executeScript(script);
    }

    public void executeScript(String script, Object... args) {
        js.executeScript(script, args);
    }

    public void click(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoView(WebElement element, boolean alignWithTop) {
        js.executeScript("arguments[0].scrollIntoView(arguments[1]);", element, alignWithTop);
    }

    /**
     * Scrolls the browser to the element x and y position perform the current page.
     */
    public void scrollToElement(WebElement element) {
        Point elementLocationOnPage = ((Locatable) element).getCoordinates().onPage();
        js.executeScript("window.scrollBy(" + elementLocationOnPage.x + "," + elementLocationOnPage.y + ");");
    }

    public void focus(WebElement element) {
        js.executeScript("arguments[0].focus();", element);
    }

    public SyntheticEvent fireEvent(WebElement element, DomEvent event) {
        js.executeScript(
                "if (document.createEvent) {" + "var eventObj = document.createEvent(arguments[2]);" + "eventObj.initEvent(arguments[1], true, true);"
                        + "arguments[0].dispatchEvent(eventObj);" + "} else if (document.createEventObject) {"
                        + "var eventObj = document.createEventObject();" + "arguments[0].fireEvent('perform' + arguments[1], eventObj);" + "}", element,
                event.getName(), event.getType());
        return this;
    }
}
