package io.magentys.cinnamon.webdriver.actions.synthetic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SyntheticHoverAction implements Action {

    private final SyntheticEvent syntheticEvent;

    public SyntheticHoverAction(final WebDriver webDriver) {
        this.syntheticEvent = new SyntheticEvent(webDriver);
    }

    public static SyntheticHoverAction syntheticHoverAction(final WebDriver webDriver) {
        return new SyntheticHoverAction(webDriver);
    }

    @Override
    public void perform(final WebElement target) {
        syntheticEvent.fireEvent(target, DomEvent.MOUSEENTER).
                fireEvent(target, DomEvent.MOUSEOVER);
    }
}