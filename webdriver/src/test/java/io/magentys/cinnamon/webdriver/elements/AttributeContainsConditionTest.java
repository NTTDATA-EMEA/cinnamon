package io.magentys.cinnamon.webdriver.elements;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import io.magentys.cinnamon.webdriver.elements.AttributeContainsCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class AttributeContainsConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenAttributeContainsValue() {
        when(mockElement.getAttribute("class")).thenReturn("menu active");
        AttributeContainsCondition condition = new AttributeContainsCondition("class", "active");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenAttributeDoesNotContainValue() {
        when(mockElement.getAttribute("style")).thenReturn("display: none");
        AttributeContainsCondition condition = new AttributeContainsCondition("style", "block");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockElement.getAttribute("class")).thenReturn(null);
        AttributeContainsCondition condition = new AttributeContainsCondition("class", "hidden");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.getAttribute("class")).thenThrow(new StaleElementReferenceException("stale"));
        AttributeContainsCondition condition = new AttributeContainsCondition("class", "active");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
