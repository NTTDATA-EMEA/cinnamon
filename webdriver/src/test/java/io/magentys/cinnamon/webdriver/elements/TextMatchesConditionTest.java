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

public class TextMatchesConditionTest {

    @Mock
    private WebElement mockElement;

    @Mock
    private WebElementConverter mockWebElementConverter;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenTextMatchesRegex() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("1234");
        TextMatchesCondition condition = new TextMatchesCondition(mockWebElementConverter, 0x00, "(\\d+)");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenTextDoesNotMatchRegex() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("abcdef");
        TextMatchesCondition condition = new TextMatchesCondition(mockWebElementConverter, 0x00, "^bcd");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn(null);
        TextMatchesCondition condition = new TextMatchesCondition(mockWebElementConverter, 0x00, "(\\s+)");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenThrow(new StaleElementReferenceException("stale"));
        TextMatchesCondition condition = new TextMatchesCondition(mockWebElementConverter, 0x00, "(\\s+)");
        assertThat(condition.apply(mockElement), equalTo(false));
    }
}