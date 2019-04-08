package io.magentys.cinnamon.webdriver.support.ui;

import com.google.common.collect.Lists;
import io.magentys.cinnamon.webdriver.conditions.Condition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

import static java.time.Instant.EPOCH;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CinnamonExpectedConditionsTest {

    @Mock
    private WebDriver mockDriver;
    @Mock
    private WebElement mockElement;
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
        wait = new FluentWait<SearchContext>(mockDriver, mockClock, mockSleeper).withTimeout(Duration.of(5, SECONDS))
                .pollingEvery(Duration.of(1000, MILLIS));

        // Set up a time series that extends past the end of the wait timeout
        when(mockClock.instant()).thenReturn(EPOCH, EPOCH.plusMillis(1000), EPOCH.plusMillis(2000), EPOCH.plusMillis(3000), EPOCH.plusMillis(4000),
                EPOCH.plusMillis(5000), EPOCH.plusMillis(6000));
    }

    @Test
    public void conditionsOfElementLocatedReturnsWebElement() {
        List<WebElement> webElements = Lists.newArrayList(mockElement);
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        when(mockCondition.apply(mockElement)).thenReturn(true);
        WebElement elementLocated = wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
        assertEquals(mockElement, elementLocated);
    }

    @Test
    public void conditionsOfElementLocatedThrowsTimeoutExceptionWhenNoElementsFound() {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("tried for 5 second");

        List<WebElement> webElements = Lists.newArrayList();
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
    }

    @Test
    public void conditionsOfElementLocatedThrowsTimeoutExceptionWhenNoElementsMatchCondition() {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("tried for 5 second");

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
        List<WebElement> allElementsLocated = wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
        assertEquals(webElements, allElementsLocated);
    }

    @Test
    public void conditionsOfAllElementsLocatedThrowsTimeoutExceptionWhenNoElementsFound() throws InterruptedException {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("tried for 5 second");

        List<WebElement> webElements = Lists.newArrayList();
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
    }

    @Test
    public void conditionsOfAllElementsLocatedThrowsTimeoutExceptionWhenNoElementsMatchCondition() {
        expectedException.expect(TimeoutException.class);
        expectedException.expectMessage("tried for 5 second");

        List<WebElement> webElements = Lists.newArrayList(mockElement);
        when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
        when(mockCondition.apply(mockElement)).thenReturn(false);
        wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
    }

}
