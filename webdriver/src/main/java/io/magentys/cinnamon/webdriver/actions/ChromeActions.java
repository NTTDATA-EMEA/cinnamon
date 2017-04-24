package io.magentys.cinnamon.webdriver.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.actions.chrome.ScrollBeforeClickAction.scrollBeforeClickAction;

class ChromeActions extends DefaultActions {

    public ChromeActions(final WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void click(final WebElement target) {scrollBeforeClickAction(webDriver).perform(target); }
}