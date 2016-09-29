package io.magentys.cinnamon.webdriver.elements;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class CssPropertyMatchesConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenCssPropertyMatchesRegex() {
        when(mockElement.getCssValue("color")).thenReturn("rgba(68, 68, 68, 1)");
        CssPropertyMatchesCondition condition = new CssPropertyMatchesCondition("color", "^rgba\\((.*)\\)$");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenCssPropertyDoesNotMatchRegex() {
        when(mockElement.getCssValue("color")).thenReturn("rgba(68, 68, 68, 1)");
        CssPropertyMatchesCondition condition = new CssPropertyMatchesCondition("color", "^rgb\\((.*)\\)$");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockElement.getCssValue("color")).thenReturn(null);
        CssPropertyEqualsCondition condition = new CssPropertyEqualsCondition("color", "^rgba\\((.*)\\)$");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.getCssValue("color")).thenThrow(new StaleElementReferenceException("stale"));
        CssPropertyEqualsCondition condition = new CssPropertyEqualsCondition("color", "^rgba\\((.*)\\)$");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
