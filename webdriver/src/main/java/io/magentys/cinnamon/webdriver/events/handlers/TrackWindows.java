package io.magentys.cinnamon.webdriver.events.handlers;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import io.magentys.cinnamon.webdriver.WebDriverContainer;
import io.magentys.cinnamon.webdriver.events.BeforeClickEvent;

public class TrackWindows {

    private final WebDriverContainer webDriverContainer;

    public TrackWindows(final WebDriverContainer webDriverContainer) {
        this.webDriverContainer = webDriverContainer;
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEvent(final BeforeClickEvent event) {
        webDriverContainer.updateWindowCount();
    }
}
