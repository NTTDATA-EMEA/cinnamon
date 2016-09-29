package io.magentys.cinnamon.webdriver.actions.basic;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static org.mockito.Mockito.*;

public class DeleteContentActionTest {

    @Mock
    private WebElement mockElement;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldInvokeBackSpaceSendKeys() {
        String value = "hello123";
        when(mockElement.getAttribute("value")).thenReturn(value);
        DeleteContentAction deleteContentAction = new DeleteContentAction();
        deleteContentAction.perform(mockElement);
        verify(mockElement, times(value.length())).sendKeys(Keys.BACK_SPACE);
    }
}