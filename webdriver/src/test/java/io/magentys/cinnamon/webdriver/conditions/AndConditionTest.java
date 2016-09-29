package io.magentys.cinnamon.webdriver.conditions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebElement;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class AndConditionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenAllConditionsAreMet() {
        when(mockElement.getAttribute("class")).thenReturn("menu active");
        when(mockElement.isDisplayed()).thenReturn(true);
        when(mockElement.isEnabled()).thenReturn(true);
        AndCondition<WebElement> condition = displayed.and(enabled).and(attributeContains("class", "active"));
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenAtLeastOneConditionIsNotMet() {
        when(mockElement.getAttribute("class")).thenReturn("menu disabled");
        when(mockElement.isDisplayed()).thenReturn(true);
        when(mockElement.isEnabled()).thenReturn(true);
        AndCondition<WebElement> condition = displayed.and(enabled).and(attributeContains("class", "active"));
        assertThat(condition.apply(mockElement), equalTo(false));
    }
}
