package io.magentys.cinnamon.webdriver.support.ui;

import org.junit.Before;
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

import io.magentys.cinnamon.webdriver.conditions.Condition;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class CinnamonExpectedConditionsTest
{
  @Mock
  private WebDriver mockDriver;

  @Mock
  private WebElement mockElement;

  @SuppressWarnings("unused")
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
  public void setUpMocks()
  {
    MockitoAnnotations.initMocks(this);
    wait = new FluentWait<SearchContext>(mockDriver, mockClock, mockSleeper).withTimeout(5, TimeUnit.SECONDS).pollingEvery(1000,
      TimeUnit.MILLISECONDS);
  }

  @Test
  public void conditionsOfElementLocatedReturnsWebElement()
  {
    List<WebElement> webElements = Lists.newArrayList(mockElement);
    when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
    when(mockCondition.apply(mockElement)).thenReturn(true);
    WebElement elementLocated = wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
    assertEquals(mockElement, elementLocated);
  }

  @Test
  public void conditionsOfElementLocatedThrowsTimeoutExceptionWhenNoElementsFound()
  {
    expectedException.expect(TimeoutException.class);
    expectedException.expectMessage("Expected condition failed: waiting for");

    List<WebElement> webElements = Lists.newArrayList();
    when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
    wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
  }

  @Test
  public void conditionsOfElementLocatedThrowsTimeoutExceptionWhenNoElementsMatchCondition()
  {
    try
    {
      List<WebElement> webElements = Lists.newArrayList(mockElement);
      when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
      when(mockCondition.apply(mockElement)).thenReturn(false);
      wait.until(CinnamonExpectedConditions.conditionOfElementLocated(By.id("someid"), mockCondition));
    }
    catch (TimeoutException e)
    {
      String msg = e.getMessage();

      assertTrue(
        msg.startsWith("Expected condition failed: waiting for io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions")
      );

      return;
    }

    fail("TimeOutException not caught");
  }

  @Test
  public void conditionsOfAllElementsLocatedReturnsWebElements()
  {
    List<WebElement> webElements = Lists.newArrayList(mockElement);
    when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
    when(mockCondition.apply(mockElement)).thenReturn(true);
    List<WebElement> allElementsLocated = wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"),
      mockCondition));
    assertEquals(webElements, allElementsLocated);
  }

  @Test
  public void conditionsOfAllElementsLocatedThrowsTimeoutExceptionWhenNoElementsFound() throws InterruptedException
  {
    try
    {
      List<WebElement> webElements = Lists.newArrayList();
      when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
      wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
    }
    catch (TimeoutException e)
    {
      String msg = e.getMessage();

      assertTrue(
        msg.startsWith("Expected condition failed: waiting for io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions")
      );

      return;
    }

    fail("TimeoutException not thrown");
  }

  @Test
  // @Ignore
  public void conditionsOfAllElementsLocatedThrowsTimeoutExceptionWhenNoElementsMatchCondition()
  {
    try
    {
      List<WebElement> webElements = Lists.newArrayList(mockElement);
      when(mockDriver.findElements(By.id("someid"))).thenReturn(webElements);
      when(mockCondition.apply(mockElement)).thenReturn(false);
      wait.until(CinnamonExpectedConditions.conditionOfAllElementsLocated(By.id("someid"), mockCondition));
    }
    catch (TimeoutException e)
    {
      String msg = e.getMessage();

      assertTrue(
        msg.startsWith("Expected condition failed: waiting for io.magentys.cinnamon.webdriver.support.ui.CinnamonExpectedConditions")
      );

      return;
    }

    fail("TimeoutException not thrown");
  }
}
