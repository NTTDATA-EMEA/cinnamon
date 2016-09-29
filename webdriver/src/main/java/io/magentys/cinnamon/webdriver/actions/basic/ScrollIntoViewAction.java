package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import io.magentys.cinnamon.webdriver.actions.synthetic.SyntheticEvent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ScrollIntoViewAction implements Action {

    private final SyntheticEvent syntheticEvent;

    public ScrollIntoViewAction(final WebDriver webDriver) {
        this.syntheticEvent = new SyntheticEvent(webDriver);
    }

    public static ScrollIntoViewAction scrollIntoViewAction(final WebDriver webDriver) {
        return new ScrollIntoViewAction(webDriver);
    }

    @Override
    public void perform(final WebElement target) {
        syntheticEvent.scrollIntoView(target, true);
    }
}