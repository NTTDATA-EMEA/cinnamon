package io.magentys.cinnamon.webdriver.collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class SizeGreatherThanConditionTest {

    @Mock
    private List<WebElement> mockElements;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenSizeGreaterThan() {
        when(mockElements.size()).thenReturn(4);
        SizeGreaterThanCondition condition = new SizeGreaterThanCondition(3);
        assertThat(condition.apply(mockElements), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenSizeNotGreaterThan() {
        when(mockElements.size()).thenReturn(3);
        SizeGreaterThanCondition condition = new SizeGreaterThanCondition(3);
        assertThat(condition.apply(mockElements), equalTo(false));
    }
}