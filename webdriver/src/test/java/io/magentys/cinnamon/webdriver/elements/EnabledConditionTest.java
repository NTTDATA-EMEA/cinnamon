package io.magentys.cinnamon.webdriver.elements;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import io.magentys.cinnamon.webdriver.elements.EnabledCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class EnabledConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenElementIsEnabled() {
        when(mockElement.isEnabled()).thenReturn(true);
        EnabledCondition condition = new EnabledCondition();
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenElementIsNotEnabled() {
        when(mockElement.isEnabled()).thenReturn(false);
        EnabledCondition condition = new EnabledCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementIsNull() {
        when(mockElement.isEnabled()).thenThrow(new NullPointerException());
        EnabledCondition condition = new EnabledCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.isEnabled()).thenThrow(new StaleElementReferenceException("stale"));
        EnabledCondition condition = new EnabledCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
