package io.magentys.cinnamon.webdriver.actions.basic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ReplaceTextActionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInvokeClearAndSendKeys() {
        ReplaceTextAction action = new ReplaceTextAction();
        action.perform(mockElement);
        verify(mockElement, times(1)).clear();
        verify(mockElement, times(1)).sendKeys();
    }
}