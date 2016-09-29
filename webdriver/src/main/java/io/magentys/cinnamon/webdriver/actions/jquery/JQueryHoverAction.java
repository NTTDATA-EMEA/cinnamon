package io.magentys.cinnamon.webdriver.actions.jquery;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JQueryHoverAction implements Action {

    private final JavascriptExecutor js;

    public JQueryHoverAction(final JavascriptExecutor js) {
        this.js = js;
    }

    public static JQueryHoverAction jQueryHoverAction(final JavascriptExecutor js) {
        return new JQueryHoverAction(js);
    }

    @Override
    public void perform(final WebElement target) {
        js.executeScript("$(arguments[0]).mouseover()", target);
    }
}