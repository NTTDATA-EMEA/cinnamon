package io.magentys.cinnamon.webdriver.actions.absolute;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

@RunWith(Parameterized.class)
public class OffsetActionTest {

    private static final int ELEMENT_X = 100;
    private static final int ELEMENT_Y = 200;

    @Parameters
    public static Collection<Object[]> params() {
        final Object[][] params = {

        { 0, 0, new Point(ELEMENT_X, ELEMENT_Y) }, { 10, 10, new Point(ELEMENT_X + 10, ELEMENT_Y + 10) },
                { 20, 50, new Point(ELEMENT_X + 20, ELEMENT_Y + 50) }, { -20, -50, new Point(ELEMENT_X - 20, ELEMENT_Y - 50) },
                { -ELEMENT_X, -ELEMENT_Y, new Point(0, 0) }, { -ELEMENT_X - 10, -ELEMENT_Y - 10, new Point(-10, -10) }, };

        return Arrays.asList(params);
    }

    @Parameter(0)
    public int x;

    @Parameter(1)
    public int y;

    @Parameter(2)
    public Point expectedPoint;

    private WebElement element;

    @Before
    public void setup() {
        element = mock(WebElement.class);
        given(element.getLocation()).willReturn(new Point(100, 200));
    }

    @Test
    public void testPerformShouldDelegateWithOffsetFromElementTopLeft() {
        final AbsolutePointAction pointAction = mock(AbsolutePointAction.class);

        new OffsetAction(x, y, pointAction).perform(element);

        then(pointAction).should().perform(expectedPoint);
    }

}
