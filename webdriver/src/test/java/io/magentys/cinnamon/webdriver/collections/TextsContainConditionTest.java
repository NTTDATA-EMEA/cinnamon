package io.magentys.cinnamon.webdriver.collections;

import io.magentys.cinnamon.webdriver.elements.WebElementConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class TextsContainConditionTest {

    @Mock
    private List<WebElement> mockElements;

    @Mock
    private WebElementConverter mockWebElementConverter;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenTextsContainValues() {
        when(mockWebElementConverter.getTextsFrom(mockElements)).thenReturn(asList("b", "text", "a"));
        TextsContainCondition condition = new TextsContainCondition(mockWebElementConverter, false, "a", "b", "text");
        assertThat(condition.apply(mockElements), equalTo(true));
    }

    @Test
    public void shouldMatchConditionIgnoringCaseWhenTextsContainValues() {
        when(mockWebElementConverter.getTextsFrom(mockElements)).thenReturn(asList("B", "Text", "A"));
        TextsContainCondition condition = new TextsContainCondition(mockWebElementConverter, true, "a", "b", "text");
        assertThat(condition.apply(mockElements), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenTextsDoNotContainValues() {
        when(mockWebElementConverter.getTextsFrom(mockElements)).thenReturn(asList("b", "tsxt", "a"));
        TextsContainCondition condition = new TextsContainCondition(mockWebElementConverter, false, "a", "b", "text");
        assertThat(condition.apply(mockElements), equalTo(false));
    }
}
