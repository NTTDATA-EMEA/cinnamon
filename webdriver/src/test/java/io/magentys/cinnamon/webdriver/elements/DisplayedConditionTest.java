package io.magentys.cinnamon.webdriver.elements;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import io.magentys.cinnamon.webdriver.elements.DisplayedCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class DisplayedConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenElementIsDisplayed() {
        when(mockElement.isDisplayed()).thenReturn(true);
        DisplayedCondition condition = new DisplayedCondition();
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenElementIsNotDisplayed() {
        when(mockElement.isDisplayed()).thenReturn(false);
        DisplayedCondition condition = new DisplayedCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementIsNull() {
        when(mockElement.isDisplayed()).thenThrow(new NullPointerException());
        DisplayedCondition condition = new DisplayedCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.isDisplayed()).thenThrow(new StaleElementReferenceException("stale"));
        DisplayedCondition condition = new DisplayedCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
