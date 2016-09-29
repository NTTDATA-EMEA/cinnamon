package io.magentys.cinnamon.webdriver.events.handlers;

import com.google.common.eventbus.Subscribe;
import io.magentys.cinnamon.events.TeardownFinishedEvent;
import io.magentys.cinnamon.webdriver.WebDriverContainer;

public class CloseExtraWindows {

    private final WebDriverContainer webDriverContainer;

    public CloseExtraWindows(final WebDriverContainer webDriverContainer) {
        this.webDriverContainer = webDriverContainer;
    }

    @Subscribe
    public void handleEvent(final TeardownFinishedEvent event) {
        if (webDriverContainer.reuseBrowserSession()) {
            webDriverContainer.closeExtraWindows();
        }
    }
}
