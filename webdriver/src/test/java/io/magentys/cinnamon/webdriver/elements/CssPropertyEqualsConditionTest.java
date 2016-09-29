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

public class CssPropertyEqualsConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenCssPropertyEqualsValue() {
        when(mockElement.getCssValue("font-weight")).thenReturn("700");
        CssPropertyEqualsCondition condition = new CssPropertyEqualsCondition("font-weight", "700");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenCssPropertyDoesNotEqualValue() {
        when(mockElement.getCssValue("font-weight")).thenReturn("700");
        CssPropertyEqualsCondition condition = new CssPropertyEqualsCondition("font-weight", "70");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockElement.getCssValue("font-weight")).thenReturn(null);
        CssPropertyEqualsCondition condition = new CssPropertyEqualsCondition("font-weight", "700");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.getCssValue("font-weight")).thenThrow(new StaleElementReferenceException("stale"));
        CssPropertyEqualsCondition condition = new CssPropertyEqualsCondition("font-weight", "700");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
