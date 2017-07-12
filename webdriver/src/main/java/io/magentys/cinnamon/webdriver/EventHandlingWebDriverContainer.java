package io.magentys.cinnamon.webdriver;

import io.magentys.cinnamon.eventbus.EventBusContainer;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.webdriver.config.CinnamonWebDriverConfig;
import io.magentys.cinnamon.webdriver.events.handlers.AttachScreenshot;
import io.magentys.cinnamon.webdriver.events.handlers.CloseExtraWindows;
import io.magentys.cinnamon.webdriver.events.handlers.QuitBrowserSession;
import io.magentys.cinnamon.webdriver.events.handlers.TrackWindows;
import io.magentys.cinnamon.webdriver.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EventHandlingWebDriverContainer implements WebDriverContainer {

    private final CinnamonWebDriverConfig cinnamonWebDriverConfig = new CinnamonWebDriverConfig();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WindowTracker> tracker = new ThreadLocal<>();
    private final List<Object> eventHandlers = Collections.synchronizedList((new ArrayList<>()));

    @Override
    public WebDriver getWebDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
            tracker.set(createWindowTracker());
            addEventHandler(new AttachScreenshot(this));
            addEventHandler(new TrackWindows(this));
            addEventHandler(new CloseExtraWindows(this));
            addEventHandler(new QuitBrowserSession(this));
            registerEventHandlers();
        }
        return driver.get();
    }

    @Override
    public WindowTracker getWindowTracker() {
        return tracker.get();
    }

    @Override
    public Boolean reuseBrowserSession() {
        return cinnamonWebDriverConfig.config().getBoolean("reuse-browser-session");
    }

    @Override
    public Attachment takeScreenshot() {
        return new Screenshot(driver.get());
    }

    @Override
    public void updateWindowCount() {
        logger.debug(String.format("Updating window count to %s", getWindowCount()));
        tracker.get().setCount(getWindowCount());
    }

    @Override
    public void closeExtraWindows() {
        List<String> windowHandles = driver.get().getWindowHandles().stream().collect(Collectors.toList());
        windowHandles.stream().skip(1).forEach(h -> driver.get().switchTo().window(h).close());
        driver.get().switchTo().window(windowHandles.get(0));
        tracker.get().setCount(1);
    }

    @Override
    public void quitWebDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
            }
        } catch (WebDriverException e) {
            logger.error(e.getMessage(), e);
        } finally {
            tracker.remove();
            driver.remove();
        }
    }

    private WebDriver createDriver() {
        Option<String> remoteUrl = Option.apply(cinnamonWebDriverConfig.hubUrl());
        return WebDriverFactory.apply().getDriver(cinnamonWebDriverConfig.driverConfig().desiredCapabilities(), remoteUrl, cinnamonWebDriverConfig.driverConfig().binaryConfig());
    }

    private WindowTracker createWindowTracker() {
        WindowTracker tracker = new WindowTracker();
        tracker.setCount(getWindowCount());
        return tracker;
    }

    private int getWindowCount() {
        return driver.get().getWindowHandles().size();
    }

    private void addEventHandler(final Object eventHandler) {
        eventHandlers.add(eventHandler);
    }

    private void registerEventHandlers() {
        eventHandlers.forEach(EventBusContainer.getEventBus()::register);
    }
}