package io.magentys.cinnamon.webdriver.actions.synthetic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SyntheticDoubleClickAction implements Action {

    private final SyntheticEvent syntheticEvent;

    public SyntheticDoubleClickAction(final WebDriver webDriver) {
        this.syntheticEvent = new SyntheticEvent(webDriver);
    }

    public static SyntheticDoubleClickAction syntheticDoubleClickAction(final WebDriver webDriver) {
        return new SyntheticDoubleClickAction(webDriver);
    }

    @Override
    public void perform(WebElement target) {
        syntheticEvent.fireEvent(target, DomEvent.DOUBLECLICK);
    }
}