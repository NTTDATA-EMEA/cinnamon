package io.magentys.cinnamon.webdriver.elements;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import io.magentys.cinnamon.webdriver.elements.SelectedCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class SelectedConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenElementIsSelected() {
        when(mockElement.isSelected()).thenReturn(true);
        SelectedCondition condition = new SelectedCondition();
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenElementIsNotSelected() {
        when(mockElement.isSelected()).thenReturn(false);
        SelectedCondition condition = new SelectedCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementIsNull() {
        when(mockElement.isSelected()).thenThrow(new NullPointerException());
        SelectedCondition condition = new SelectedCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.isSelected()).thenThrow(new StaleElementReferenceException("stale"));
        SelectedCondition condition = new SelectedCondition();
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
