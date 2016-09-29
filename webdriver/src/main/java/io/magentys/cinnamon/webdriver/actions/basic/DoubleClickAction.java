package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DoubleClickAction implements Action {

    private final Actions actions;

    public DoubleClickAction(final WebDriver webDriver) {
        this.actions = new Actions(webDriver);
    }

    public static DoubleClickAction doubleClickAction(final WebDriver webDriver) {
        return new DoubleClickAction(webDriver);
    }

    @Override
    public void perform(final WebElement target) {
        actions.moveToElement(target).doubleClick().build().perform();
    }
}