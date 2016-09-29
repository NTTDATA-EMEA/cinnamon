package io.magentys.cinnamon.webdriver.support.ui;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.stream.Collectors;

public class CinnamonExpectedConditions {

    public static ExpectedCondition<Boolean> conditionToBe(final Condition<WebDriver> condition) {
        return condition::apply;
    }

    public static Function<SearchContext, List<WebElement>> conditionOfAllElementsLocated(final By by, final Condition<WebElement> condition) {
        return context -> {
            try {
                final List<WebElement> elements = Lists.newArrayList(Iterables.filter(context.findElements(by), condition));
                return elements.isEmpty() ? null : elements;
            } catch (final WebDriverException e) {
                return null;
            }
        };
    }

    public static Function<SearchContext, WebElement> conditionOfElementLocated(final By by, final Condition<WebElement> condition) {
        return context -> {
            try {
                final List<WebElement> foundElements = context.findElements(by);
                for (final WebElement element : foundElements) {
                    if (condition.apply(element)) {
                        return element;
                    }
                }
                return null;
            } catch (final WebDriverException e) {
                return null;
            }
        };
    }

    public static Function<SearchContext, Boolean> elementCountToBe(final By by, final Condition<WebElement> condition, final int count) {
        return context -> {
            try {
                final Iterable<WebElement> elements = Iterables.filter(context.findElements(by), condition);
                return Lists.newArrayList(elements).size() == count;
            } catch (final WebDriverException e) {
                return false;
            }
        };
    }

    public static ExpectedCondition<WebDriver> windowToBeAvailableAndSwitchToIt(final int zeroBasedIndex) {
        return driver -> {
            try {
                final List<String> windowHandles = driver.getWindowHandles().stream().collect(Collectors.toList());
                return driver.switchTo().window(windowHandles.get(zeroBasedIndex));
            } catch (IndexOutOfBoundsException e) {
                return null;
            } catch (WebDriverException e) {
                return null;
            }
        };
    }

    public static ExpectedCondition<WebDriver> windowToBeAvailableAndSwitchToIt(final String title) {
        return windowToBeAvailableAndSwitchToIt(title, false);
    }

    public static ExpectedCondition<WebDriver> windowToBeAvailableAndSwitchToIt(final String title, final boolean partial) {
        return driver -> {
            final List<String> windowHandles = driver.getWindowHandles().stream().collect(Collectors.toList());
            try {
                for (String windowHandle : windowHandles) {
                    driver.switchTo().window(windowHandle);
                    if (partial ? driver.getTitle().contains(title) : title.equals(driver.getTitle())) {
                        return driver;
                    }
                }
                driver.switchTo().window(windowHandles.get(0));
                return null;
            } catch (WebDriverException e) {
                driver.switchTo().window(windowHandles.get(0));
                return null;
            }
        };
    }
}
