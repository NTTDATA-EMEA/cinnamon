package io.magentys.cinnamon.webdriver.actions.basic;

import io.magentys.cinnamon.webdriver.actions.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HoverAction implements Action {

    private final WebDriver webDriver;

    public HoverAction(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public static HoverAction hoverAction(final WebDriver webDriver) {
        return new HoverAction(webDriver);
    }

    @Override
    public void perform(final WebElement target) {
        final Actions actions = new Actions(webDriver);
        actions.moveToElement(target).perform();
    }
}