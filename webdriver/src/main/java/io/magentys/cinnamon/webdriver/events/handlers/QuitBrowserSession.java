package io.magentys.cinnamon.webdriver.events.handlers;

import com.google.common.eventbus.Subscribe;
import io.magentys.cinnamon.events.TestCaseFinishedEvent;
import io.magentys.cinnamon.events.TestRunnerFinishedEvent;
import io.magentys.cinnamon.webdriver.WebDriverContainer;

public class QuitBrowserSession {

    private final WebDriverContainer webDriverContainer;

    public QuitBrowserSession(final WebDriverContainer webDriverContainer) {
        this.webDriverContainer = webDriverContainer;
    }

    @Subscribe
    public void handleEvent(final TestCaseFinishedEvent event) {
        if (!webDriverContainer.reuseBrowserSession()) {
            webDriverContainer.quitWebDriver();
        }
    }

    @Subscribe
    public void handleEvent(final TestRunnerFinishedEvent event) {
        webDriverContainer.quitWebDriver();
    }
}
