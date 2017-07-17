package io.magentys.cinnamon.webdriver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class CinnamonWebDriverOptionsTest {

    @Mock(extraInterfaces = { JavascriptExecutor.class, HasCapabilities.class })
    private WebDriver mockDriver;

    @Before
    public void setUpMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldHandleEmptyCookiesForBrowsersThatRequireJavaScript() {
        when(((HasCapabilities) mockDriver).getCapabilities()).thenReturn(DesiredCapabilities.safari());
        doReturn("").when((JavascriptExecutor) mockDriver).executeScript("return (document.cookie);");
        CinnamonWebDriverOptions options = new CinnamonWebDriverOptions(mockDriver);
        assertThat(options.getCookies(), emptyCollectionOf(Cookie.class));
    }

    @Test
    public void shouldHandleCookieWithEmptyValueForBrowsersThatRequireJavaScript() {
        when(((HasCapabilities) mockDriver).getCapabilities()).thenReturn(DesiredCapabilities.safari());
        doReturn("cookiea=;cookieb=123;cookiec=").when((JavascriptExecutor) mockDriver).executeScript("return (document.cookie);");
        CinnamonWebDriverOptions options = new CinnamonWebDriverOptions(mockDriver);
        assertThat(options.getCookieNamed("cookiea").getValue(), is(""));
        assertThat(options.getCookieNamed("cookieb").getValue(), is("123"));
    }

    @Test
    public void shouldReturnNullForNonExistentCookie() {
        when(((HasCapabilities) mockDriver).getCapabilities()).thenReturn(DesiredCapabilities.safari());
        doReturn("cookiea=123").when((JavascriptExecutor) mockDriver).executeScript("return (document.cookie);");
        CinnamonWebDriverOptions options = new CinnamonWebDriverOptions(mockDriver);
        assertThat(options.getCookieNamed("nonExistentCookie"), is(nullValue()));
    }
}
