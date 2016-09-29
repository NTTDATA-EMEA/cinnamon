package io.magentys.cinnamon.webdriver.actions.absolute;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

class AbsoluteClickAction implements AbsolutePointAction {

    private final WebDriver webDriver;

    public AbsoluteClickAction(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public void perform(final Point target) {
        new Actions(webDriver).moveByOffset(target.x, target.y).click().perform();
    }
}