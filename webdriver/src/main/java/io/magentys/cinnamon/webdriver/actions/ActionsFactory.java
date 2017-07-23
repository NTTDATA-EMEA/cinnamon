package io.magentys.cinnamon.webdriver.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.getBrowserName;

public class ActionsFactory {

    public static Actions create(final WebDriver webDriver) {
        switch (getBrowserName(webDriver)) {
        case BrowserType.CHROME:
            return new ChromeActions(webDriver);
        case BrowserType.IE:
            return new InternetExplorerActions(webDriver);
        case BrowserType.FIREFOX:
        case BrowserType.SAFARI:
            return new SyntheticActions(webDriver);
        default:
            return new DefaultActions(webDriver);
        }
    }
}
