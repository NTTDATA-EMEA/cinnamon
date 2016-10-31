package io.magentys.cinnamon.webdriver;

import io.magentys.cinnamon.events.Attachment;
import org.openqa.selenium.WebDriver;

public interface WebDriverContainer {

    WebDriver getWebDriver();

    WindowTracker getWindowTracker();

    Boolean reuseBrowserSession();

    Attachment takeScreenshot();

    void updateWindowCount();

    void closeExtraWindows();

    void quitWebDriver();

    static WebDriverContainer getWebDriverContainer() {
        return new EventHandlingWebDriverContainer();
    }
}
