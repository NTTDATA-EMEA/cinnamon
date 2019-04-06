package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.BrowserType;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public final class WebDriverUtils {

    // Suppresses default constructor, ensuring non-instantiability.
    private WebDriverUtils() {
    }

    public static WebDriver unwrapDriver(final WebElement element) {
        return ((WrapsDriver) element).getWrappedDriver();
    }

    public static String getBrowserName(final WebDriver webDriver) {
        return ((HasCapabilities) webDriver).getCapabilities().getBrowserName();
    }

    public static boolean isSafari(final WebDriver webDriver) {
        return BrowserType.SAFARI.equals(getBrowserName(webDriver));
    }

    public static boolean isInternetExplorer(final WebDriver webDriver) {
        return BrowserType.IE.equals(getBrowserName(webDriver));
    }

    public static boolean isPhantomJs(final WebDriver webDriver) {
        return BrowserType.PHANTOMJS.equals(getBrowserName(webDriver));
    }

    public static String getElementXPath(final WebDriver webDriver, final WebElement element) {
        return (String) ((JavascriptExecutor) webDriver).executeScript(
                "gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return '//'+c.tagName.toLowerCase()}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName.toLowerCase()+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]);",
                element);
    }

    public static ChronoUnit toChronoUnit(TimeUnit timeUnit) {
        switch (timeUnit) {
        case NANOSECONDS:
            return ChronoUnit.NANOS;
        case MICROSECONDS:
            return ChronoUnit.MICROS;
        case MILLISECONDS:
            return ChronoUnit.MILLIS;
        case SECONDS:
            return ChronoUnit.SECONDS;
        case MINUTES:
            return ChronoUnit.MINUTES;
        case HOURS:
            return ChronoUnit.HOURS;
        case DAYS:
            return ChronoUnit.DAYS;
        default:
            throw new IllegalArgumentException("No ChronoUnit equivalent for " + timeUnit);
        }
    }
}