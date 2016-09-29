package io.magentys.cinnamon.webdriver.elements;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import io.magentys.cinnamon.webdriver.conditions.AjaxFinishedCondition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class AjaxFinishedConditionTest {

    @Mock(extraInterfaces = JavascriptExecutor.class)
    private WebDriver mockDriver;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenAjaxFinished() {
        doReturn(true).when((JavascriptExecutor) mockDriver).executeScript("return (typeof jQuery !== 'undefined' ? jQuery.active == 0 : true);");
        AjaxFinishedCondition condition = new AjaxFinishedCondition();
        assertThat(condition.apply(mockDriver), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenAjaxNotFinished() {
        doReturn(false).when((JavascriptExecutor) mockDriver).executeScript("return (typeof jQuery !== 'undefined' ? jQuery.active == 0 : true);");
        AjaxFinishedCondition condition = new AjaxFinishedCondition();
        assertThat(condition.apply(mockDriver), equalTo(false));
    }

}
