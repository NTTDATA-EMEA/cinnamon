package io.magentys.cinnamon.webdriver.actions.basic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClearActionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInvokeClearOnWebElement() {
        ClearAction action = new ClearAction();
        action.perform(mockElement);
        verify(mockElement, times(1)).clear();
    }
}