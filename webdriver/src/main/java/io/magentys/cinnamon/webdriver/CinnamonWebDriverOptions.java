package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.*;

public class CinnamonWebDriverOptions implements WebDriver.Options {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebDriver.Options delegate;
    private final JavascriptExecutor js;
    private final boolean requiresJavaScript;

    public CinnamonWebDriverOptions(final WebDriver webDriver) {
        this.delegate = webDriver.manage();
        this.js = (JavascriptExecutor) webDriver;
        this.requiresJavaScript = isInternetExplorer(webDriver) || isSafari(webDriver) || isPhantomJs(webDriver);
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (requiresJavaScript) {
            if (cookie.isHttpOnly()) {
                logger.warn("Cannot set httpOnly cookie using javascript.");
            }
            js.executeScript(cookie.isSecure() ?
                    "document.cookie = '" + cookie.getName() + "=" + cookie.getValue() + "; path=/; secure'" :
                    "document.cookie = '" + cookie.getName() + "=" + cookie.getValue() + "; path=/'");
            return;
        }
        delegate.addCookie(cookie);
    }

    @Override
    public void deleteCookieNamed(String name) {
        if (requiresJavaScript) {
            Cookie cookie = getCookieNamed(name);
            js.executeScript("document.cookie = '" + cookie.getName() + "=" + cookie.getValue() + "; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/'");
            return;
        }
        delegate.deleteCookieNamed(name);
    }

    @Override
    public void deleteCookie(Cookie cookie) {
        deleteCookieNamed(cookie.getName());
    }

    @Override
    public void deleteAllCookies() {
        getCookies().forEach(this::deleteCookie);
    }

    @Override
    public Set<Cookie> getCookies() {
        if (requiresJavaScript) {
            return Stream.of(((String) js.executeScript("return (document.cookie);")).split(";")).filter(s -> !s.isEmpty()).map(c -> {
                String[] parts = c.trim().split("=", -1);
                return new Cookie(parts[0], parts[1]);
            }).collect(Collectors.toSet());
        }
        return delegate.getCookies();
    }

    @Override
    public Cookie getCookieNamed(String name) {
        return getCookies().stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public WebDriver.Timeouts timeouts() {
        return delegate.timeouts();
    }

    @Override
    public WebDriver.ImeHandler ime() {
        return delegate.ime();
    }

    @Override
    public WebDriver.Window window() {
        return delegate.window();
    }

    @Override
    public Logs logs() {
        return delegate.logs();
    }
}
