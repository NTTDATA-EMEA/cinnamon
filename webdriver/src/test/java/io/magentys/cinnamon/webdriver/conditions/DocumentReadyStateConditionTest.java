package io.magentys.cinnamon.webdriver.conditions;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import io.magentys.cinnamon.webdriver.ReadyState;

import io.magentys.cinnamon.webdriver.conditions.DocumentReadyStateCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class DocumentReadyStateConditionTest {

    @Mock(extraInterfaces = JavascriptExecutor.class)
    private WebDriver mockDriver;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenReadyStateEqualsStatus() {
        ReadyState readyState = ReadyState.COMPLETE;
        doReturn(true).when((JavascriptExecutor) mockDriver).executeScript("return (document.readyState == arguments[0]);",
                readyState.getLoadingStatus());
        DocumentReadyStateCondition condition = new DocumentReadyStateCondition(readyState);
        assertThat(condition.apply(mockDriver), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenReadyStateDoesNotEqualValue() {
        ReadyState readyState = ReadyState.COMPLETE;
        doReturn(false).when((JavascriptExecutor) mockDriver).executeScript("return (document.readyState == arguments[0]);",
                readyState.getLoadingStatus());
        DocumentReadyStateCondition condition = new DocumentReadyStateCondition(readyState);
        assertThat(condition.apply(mockDriver), equalTo(false));
    }

}
