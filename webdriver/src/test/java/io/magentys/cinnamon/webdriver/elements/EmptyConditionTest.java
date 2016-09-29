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

public class EmptyConditionTest {

    @Mock
    private WebElement mockElement;

    @Mock
    private WebElementConverter mockWebElementConverter;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenElementTextIsEmpty() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("");
        EmptyCondition condition = new EmptyCondition(mockWebElementConverter);
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenElementTextIsNotEmpty() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenReturn("Test");
        EmptyCondition condition = new EmptyCondition(mockWebElementConverter);
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementIsNull() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenThrow(new NullPointerException());
        EmptyCondition condition = new EmptyCondition(mockWebElementConverter);
        assertThat(condition.apply(mockElement), equalTo(false));
    }

    @Test
    public void shouldHandleExceptionWhenElementHasStaleReference() {
        when(mockWebElementConverter.getTextFrom(mockElement)).thenThrow(new StaleElementReferenceException("stale"));
        EmptyCondition condition = new EmptyCondition(mockWebElementConverter);
        assertThat(condition.apply(mockElement), equalTo(false));
    }
}