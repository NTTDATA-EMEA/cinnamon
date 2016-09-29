package io.magentys.cinnamon.webdriver.support.pagefactory;

import io.magentys.cinnamon.webdriver.Timeout;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import io.magentys.cinnamon.webdriver.support.ui.SearchContextWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.present;
import static io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions.conditionOfAllElementsLocated;
import static io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions.conditionOfElementLocated;

public class PageElementLocator implements ElementLocator {

    private final WebDriver webDriver;
    private final SearchContext context;
    private final By by;
    private final Condition<WebElement> condition;
    private final Timeout timeout;

    public PageElementLocator(final WebDriver webDriver, final SearchContext context, final Field field) {
        this(webDriver, context, new Annotations(field));
    }

    public PageElementLocator(final WebDriver webDriver, final SearchContext context, final Annotations annotations) {
        this(webDriver, context, annotations.buildBy(), annotations.buildCondition(), annotations.buildTimeout());
    }

    public PageElementLocator(final WebDriver webDriver, final By by) {
        this(webDriver, webDriver, by, present, defaultTimeout());
    }

    public PageElementLocator(final WebDriver webDriver, final SearchContext context, final By by) {
        this(webDriver, context, by, present, defaultTimeout());
    }

    public PageElementLocator(final WebDriver webDriver, final By by, final Condition<WebElement> condition, final Timeout timeout) {
        this(webDriver, webDriver, by, condition, timeout);
    }

    public PageElementLocator(final WebDriver webDriver, final SearchContext context, final By by, final Condition<WebElement> condition,
            final Timeout timeout) {
        this.webDriver = webDriver;
        this.context = context;
        this.by = by;
        this.condition = condition;
        this.timeout = timeout;
    }

    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public SearchContext getSearchContext() {
        return context;
    }

    @Override
    public By getBy() {
        return by;
    }

    @Override
    public Condition<WebElement> getCondition() {
        return condition;
    }

    @Override
    public Timeout getTimeout() {
        return timeout;
    }

    @Override
    public WebElement findElement() {
        try {
            final SearchContextWait wait = new SearchContextWait(context, timeout.getMillis());
            return wait.until(conditionOfElementLocated(by, condition));
        } catch (TimeoutException e) {
            return null;
        }
    }

    @Override
    public List<WebElement> findElements() {
        try {
            final SearchContextWait wait = new SearchContextWait(context, timeout.getMillis());
            return wait.until(conditionOfAllElementsLocated(by, condition));
        } catch (TimeoutException e) {
            return Collections.emptyList();
        }
    }
}