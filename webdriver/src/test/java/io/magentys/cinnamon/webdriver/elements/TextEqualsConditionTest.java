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

public class TextEqualsConditionTest {

    @Mock
    private WebElement mockElement;

    @Mock
    private WebElementConverter mockWebElementConverter;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenTextEqualsValue() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("Test 1");
        TextEqualsCondition condition = new TextEqualsCondition(mockWebElementConverter, false, "Test 1");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldMatchConditionIgnoringCaseWhenTextEqualsValue() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("TEST 1");
        TextEqualsCondition condition = new TextEqualsCondition(mockWebElementConverter, true, "Test 1");
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenTextDoesNotEqualValue() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("Test 2");
        TextEqualsCondition condition = new TextEqualsCondition(mockWebElementConverter, false, "Test 1");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleNullReturn() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn(null);
        TextEqualsCondition condition = new TextEqualsCondition(mockWebElementConverter, false, "some text");
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenThrow(new StaleElementReferenceException("stale"));
        TextEqualsCondition condition = new TextEqualsCondition(mockWebElementConverter, false, "some text");
        assertThat(condition.apply(mockElement), equalTo(false));
    }
}