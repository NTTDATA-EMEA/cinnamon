package io.magentys.cinnamon.webdriver.support.ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import io.magentys.cinnamon.webdriver.conditions.Condition;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import com.google.common.collect.Lists;

public class CinnamonExpectedConditionsTest {

    @Mock
    private WebDriver mockDriver;
    @Mock
    private WebElement mockElement;
    @Mock
    private WebElement mockNestedElement;
    @Mock
    private Clock mockClock;
    @Mock
    private Sleeper mockSleeper;
    @Mock
    private Condition<WebElement> mockCondition;

    private FluentWait<SearchContext> wait;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
        wait = new FluentWait<SearchContext>(mockDriver, mockClock, mockSleeper).withTimeout(5, TimeUnit.SECONDS).pollingEvery(1000,
                TimeUnit.MILLISECONDS);
    }

    @Test
    // @Ignore
    public void conditionsOfElementLocatedReturnsWebElement() {
        List<WebElement> webElements = Lists.newArrayList(mockElement);
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        when(mockCondition.apply(mockElement)).thenReturn(true);
        WebElement elementLocated = wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
        assertEquals(mockElement, elementLocated);
    }

    @Test
    @Ignore
    public void conditionsOfElementLocatedThrowsTimeoutExceptionWhenNoElementsFound() {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("Timed out after 5 seconds");

        List<WebElement> webElements = Lists.newArrayList();
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
    }

    @Test
    @Ignore
    public void conditionsOfElementLocatedThrowsTimeoutExceptionWhenNoElementsMatchCondition() {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("Timed out after 5 seconds");

        List<WebElement> webElements = Lists.newArrayList(mockElement);
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        when(mockCondition.apply(mockElement)).thenReturn(false);
        wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
    }

    @Test
    public void conditionsOfAllElementsLocatedReturnsWebElements() {
        List<WebElement> webElements = Lists.newArrayList(mockElement);
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        when(mockCondition.apply(mockElement)).thenReturn(true);
        List<WebElement> allElementsLocated = wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"),
                mockCondition));
        assertEquals(webElements, allElementsLocated);
    }

    @Test
    @Ignore
    public void conditionsOfAllElementsLocatedThrowsTimeoutExceptionWhenNoElementsFound() throws InterruptedException {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("Timed out after 5 seconds");

        List<WebElement> webElements = Lists.newArrayList();
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
    }

    @Test
    @Ignore
    public void conditionsOfAllElementsLocatedThrowsTimeoutExceptionWhenNoElementsMatchCondition() {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("Timed out after 5 seconds");

        List<WebElement> webElements = Lists.newArrayList(mockElement);
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        when(mockCondition.apply(mockElement)).thenReturn(false);
        wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
    }
}
