package io.magentys.cinnamon.webdriver.elements;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.textContains;
import static org.mockito.Mockito.when;

public class WebElementWrapperTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock(extraInterfaces = HasCapabilities.class)
    private WebDriver mockWebDriver;

    @Mock
    private WebElement mockElement;

    @Mock
    private ElementLocator mockElementLocator;

    @Mock
    private DesiredCapabilities mockCapabilities;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldThrowNoSuchElementExceptionWhenCachedElementIsNull() {
        thrown.expect(NoSuchElementException.class);
        when(((HasCapabilities) mockWebDriver).getCapabilities()).thenReturn(mockCapabilities);
        when(mockCapabilities.getBrowserName()).thenReturn("chrome");
        when(mockElementLocator.findElement()).thenReturn(null);
        when(mockElementLocator.getBy()).thenReturn(By.cssSelector("#myId"));
        when(mockElementLocator.getCondition()).thenReturn(textContains("abc"));
        PageElement pageElement = new PageElementImpl(mockWebDriver, mockElementLocator, null);
        pageElement.getWrappedElement();
    }
}
