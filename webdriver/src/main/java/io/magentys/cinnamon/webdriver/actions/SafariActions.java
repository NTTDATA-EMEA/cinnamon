package io.magentys.cinnamon.webdriver.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.actions.synthetic.SyntheticDoubleClickAction.syntheticDoubleClickAction;
import static io.magentys.cinnamon.webdriver.actions.synthetic.SyntheticHoverAction.syntheticHoverAction;

class SafariActions extends DefaultActions {

    public SafariActions(final WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void hoverOver(final WebElement target) {
        syntheticHoverAction(webDriver).perform(target);
    }

    @Override
    public void doubleClick(final WebElement target) {
        syntheticDoubleClickAction(webDriver).perform(target);
    }
}