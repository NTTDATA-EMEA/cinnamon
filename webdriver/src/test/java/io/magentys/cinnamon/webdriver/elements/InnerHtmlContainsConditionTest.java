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

public class InnerHtmlContainsConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenInnerHtmlContainsValue() {
        when(mockElement.getAttribute("innerHTML")).thenReturn("<div>blah</div>");
        InnerHtmlContainsCondition condition = new InnerHtmlContainsCondition("blah");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldMatchConditionWhenInnerHtmlContainsValueIgnoringCase() {
        when(mockElement.getAttribute("innerHTML")).thenReturn("<div>blah</div>");
        InnerHtmlContainsCondition condition = new InnerHtmlContainsCondition(true, "BlaH");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenInnerHtmlDoesNotContainValue() {
        when(mockElement.getAttribute("innerHTML")).thenReturn("<div>blah</div>");
        InnerHtmlContainsCondition condition = new InnerHtmlContainsCondition("blsss");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNull() {
        when(mockElement.getAttribute("innerHTML")).thenReturn(null);
        InnerHtmlContainsCondition condition = new InnerHtmlContainsCondition("blah");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockElement.getAttribute("innerHTML")).thenThrow(new StaleElementReferenceException("stale"));
        InnerHtmlContainsCondition condition = new InnerHtmlContainsCondition("blah");
        assertThat(condition.apply(mockElement), equalTo(false));
    }
}