package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions.windowToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class CinnamonTargetLocator {

    private final WebDriver webDriver;
    private final WindowTracker windowTracker;

    public CinnamonTargetLocator(final WebDriver webDriver, final WindowTracker windowTracker) {
        this.webDriver = webDriver;
        this.windowTracker = windowTracker;
    }

    public SwitchToWindow window() {
        return new SwitchToWindowImpl(webDriver);
    }

    public WebDriver newWindow() {
        return newWindow(defaultTimeout());
    }

    public WebDriver newWindow(final Timeout timeout) {
        try {
            final FluentWait<WebDriver> wait = new WebDriverWait(webDriver, timeout.getSeconds());
            int windowIndex = windowTracker.getCount(); //Zero-based so does not need to be incremented
            return wait.until(windowToBeAvailableAndSwitchToIt(windowIndex));
        } catch (TimeoutException e) {
            throw new NoSuchWindowException(e.getMessage());
        }
    }

    public WebDriver frame(int frameIndex) {
        return webDriver.switchTo().frame(frameIndex);
    }

    public WebDriver frame(String frameName) {
        return webDriver.switchTo().frame(frameName);
    }

    public WebDriver frame(WebElement frameElement) {
        return webDriver.switchTo().frame(frameElement);
    }

    public WebDriver parentFrame() {
        return webDriver.switchTo().parentFrame();
    }

    public WebDriver defaultContent() {
        return webDriver.switchTo().defaultContent();
    }

    public WebElement activeElement() {
        return webDriver.switchTo().activeElement();
    }

    public Alert alert() {
        return alert(defaultTimeout());
    }

    public Alert alert(final Timeout timeout) {
        try {
            final FluentWait<WebDriver> wait = new WebDriverWait(webDriver, timeout.getSeconds());
            return new CinnamonAlert(webDriver, wait.until(alertIsPresent()));
        } catch (TimeoutException e) {
            throw new NoAlertPresentException(e.getMessage());
        }
    }
}