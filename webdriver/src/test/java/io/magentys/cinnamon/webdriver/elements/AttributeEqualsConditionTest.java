package io.magentys.cinnamon.webdriver.elements;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import io.magentys.cinnamon.webdriver.elements.AttributeEqualsCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class AttributeEqualsConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenAttributeEqualsValue() {
        when(mockElement.getAttribute("class")).thenReturn("active");
        AttributeEqualsCondition condition = new AttributeEqualsCondition("class", "active");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenAttributeDoesNotEqualValue() {
        when(mockElement.getAttribute("style")).thenReturn("display: none");
        AttributeEqualsCondition condition = new AttributeEqualsCondition("style", "display: block");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockElement.getAttribute("class")).thenReturn(null);
        AttributeEqualsCondition condition = new AttributeEqualsCondition("class", "menu");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.getAttribute("class")).thenThrow(new StaleElementReferenceException("stale"));
        AttributeEqualsCondition condition = new AttributeEqualsCondition("class", "menu");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
