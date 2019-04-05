package io.magentys.cinnamon.webdriver.elements;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.internal.Coordinates;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PositionUnchangedConditionTest {

    @Mock(extraInterfaces = Locatable.class)
    private WebElement mockElement;

    @Mock
    private Coordinates mockCoordinates;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldMatchConditionWhenElementPositionUnchanged() {
        when(((Locatable) mockElement).getCoordinates()).thenReturn(mockCoordinates);
        when(((Locatable) mockElement).getCoordinates().inViewPort()).thenReturn(new Point(100, 100), new Point(100, 100));
        PositionUnchangedCondition condition = new PositionUnchangedCondition(100);
        assertThat(condition.apply(mockElement), equalTo(true));
    }

    @Test
    public void shouldNotMatchConditionWhenElementPositionHasChanged() {
        when(((Locatable) mockElement).getCoordinates()).thenReturn(mockCoordinates);
        when(((Locatable) mockElement).getCoordinates().inViewPort()).thenReturn(new Point(100, 100), new Point(150, 100));
        PositionUnchangedCondition condition = new PositionUnchangedCondition(100);
        assertThat(condition.apply(mockElement), equalTo(false));
    }

}
