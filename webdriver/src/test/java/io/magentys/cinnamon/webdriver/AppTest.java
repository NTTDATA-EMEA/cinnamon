package io.magentys.cinnamon.webdriver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;

import static io.magentys.cinnamon.webdriver.App.closeApp;
import static io.magentys.cinnamon.webdriver.App.getAppiumDriver;
import static io.magentys.cinnamon.webdriver.Browser.getWebDriver;
import static org.mockito.Mockito.*;

public class AppTest {

    private AppiumDriver mockDriver;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCloseAppAndroid() {
        when(getWebDriver()).thenReturn(new AppiumDriver(DesiredCapabilities.android()));
        closeApp();
        verify(getAppiumDriver(), times(1)).closeApp();
    }

}
