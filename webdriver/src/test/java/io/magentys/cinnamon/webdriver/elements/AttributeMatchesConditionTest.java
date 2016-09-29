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

public class AttributeMatchesConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenAttributeMatchesRegex() {
        when(mockElement.getAttribute("class")).thenReturn("myclass modifier1 modifier2");
        AttributeMatchesCondition condition = new AttributeMatchesCondition("class", "(.*)modifier1(.*)");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenAttributeDoesNotMatchRegex() {
        when(mockElement.getAttribute("clas")).thenReturn("myclass modifier3 modifier4");
        AttributeMatchesCondition condition = new AttributeMatchesCondition("class", "(.*)modifier1(.*)");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockElement.getAttribute("class")).thenReturn(null);
        AttributeMatchesCondition condition = new AttributeMatchesCondition("class", "(.*)modifier1(.*)");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.getAttribute("class")).thenThrow(new StaleElementReferenceException("stale"));
        AttributeMatchesCondition condition = new AttributeMatchesCondition("class", "(.*)modifier1(.*)");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}