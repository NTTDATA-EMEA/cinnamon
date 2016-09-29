package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions.windowToBeAvailableAndSwitchToIt;

public class SwitchToWindowImpl implements SwitchToWindow {

    private final WebDriver webDriver;

    public SwitchToWindowImpl(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public WebDriver first() {
        return nth(0);
    }

    @Override
    public WebDriver last() {
        return nth(webDriver.getWindowHandles().size() - 1);
    }

    private WebDriver nth(final int zeroBasedIndex) {
        try {
            final List<String> windowHandles = webDriver.getWindowHandles().stream().collect(Collectors.toList());
            return webDriver.switchTo().window(windowHandles.get(zeroBasedIndex));
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchWindowException(e.getMessage(), e);
        }
    }

    @Override
    public WebDriver byTitle(final String title) {
        try {
            final FluentWait<WebDriver> wait = new WebDriverWait(webDriver, defaultTimeout().getSeconds());
            return wait.until(windowToBeAvailableAndSwitchToIt(title));
        } catch (TimeoutException e) {
            throw new NoSuchWindowException(String.format("Cannot find the window with title %s.", title));
        }
    }

    @Override
    public WebDriver byPartialTitle(final String partialTitle) {
        try {
            final FluentWait<WebDriver> wait = new WebDriverWait(webDriver, defaultTimeout().getSeconds());
            return wait.until(windowToBeAvailableAndSwitchToIt(partialTitle, true));
        } catch (TimeoutException e) {
            throw new NoSuchWindowException(String.format("Cannot find the window with partial title %s.", partialTitle));
        }
    }
}
