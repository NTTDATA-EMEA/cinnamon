package io.magentys.cinnamon.webdriver;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import io.magentys.cinnamon.eventbus.EventBusContainer;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.webdriver.config.CinnamonWebDriverConfig;
import io.magentys.cinnamon.webdriver.events.handlers.AttachScreenshot;
import io.magentys.cinnamon.webdriver.events.handlers.CloseExtraWindows;
import io.magentys.cinnamon.webdriver.events.handlers.QuitBrowserSession;
import io.magentys.cinnamon.webdriver.events.handlers.TrackWindows;
import io.magentys.cinnamon.webdriver.factory.WebDriverFactory;
import io.magentys.cinnamon.webdriver.remote.PerfectoLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventHandlingWebDriverContainer implements WebDriverContainer {

    private final CinnamonWebDriverConfig cinnamonWebDriverConfig = new CinnamonWebDriverConfig();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WindowTracker> tracker = new ThreadLocal<>();
    private final List<Object> eventHandlers = Collections.synchronizedList((new ArrayList<>()));
    protected static ReportiumClient reportiumClient;

    @Override
    public WebDriver getWebDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
            //TODO This needs to be instantiated in the Perfecto module
//            reportiumClient = createRemoteReportiumClient(driver.get());

            Optional<Object> app = Optional.ofNullable(cinnamonWebDriverConfig.driverConfig().desiredCapabilities().getCapability("app"));
            if (!app.isPresent()) {
                tracker.set(createWindowTracker());
                addEventHandler(new TrackWindows(this));
                addEventHandler(new CloseExtraWindows(this));
            }
            addEventHandler(new AttachScreenshot(this));
            //TODO This needs to be moved to the Perfecto module
//            addEventHandler(new PerfectoLogger(reportiumClient));
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
        return WebDriverFactory.apply()
                .getDriver(cinnamonWebDriverConfig.driverConfig().desiredCapabilities(), remoteUrl, cinnamonWebDriverConfig.driverConfig().exePath(),
                        cinnamonWebDriverConfig.driverConfig().driverBinary());
    }

    //TODO This needs to be moved into the Perfecto module
    private static ReportiumClient createRemoteReportiumClient(WebDriver driver) {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Reportium project", "1.0")).withWebDriver(driver).build();
        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
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