package io.magentys.cinnamon.webdriver;

import io.magentys.cinnamon.webdriver.locators.LocatorRegistry;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ByKey extends By {

    private final String locatorKey;

    public ByKey(final String locatorKey) {
        if (locatorKey == null)
            throw new IllegalArgumentException("Cannot find elements when locator key is null.");
        this.locatorKey = locatorKey;
    }

    public static By locatorKey(final String locatorKey) {
        return new ByKey(locatorKey);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openqa.selenium.By#findElement(org.openqa.selenium.SearchContext)
     */
    @Override
    public WebElement findElement(SearchContext context) {
        By by = getLocator(locatorKey);
        return by.findElement(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openqa.selenium.By#findElements(org.openqa.selenium.SearchContext)
     */
    @Override
    public List<WebElement> findElements(SearchContext context) {
        By by = getLocator(locatorKey);
        return by.findElements(context);
    }

    private By getLocator(String locatorKey) {
        try {
            return SingletonLocatorRegistry.getInstance().getLocator(locatorKey);
        } catch (ExceptionInInitializerError | NoClassDefFoundError e) {
            throw new Error("An error occurred. Please refer to the logs as it is likely a locator yaml file contains invalid content.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.openqa.selenium.By#toString()
     */
    @Override
    public String toString() {
        return "By.locatorKey: " + locatorKey;
    }

    static class SingletonLocatorRegistry {
        private SingletonLocatorRegistry() {
        }

        private static class LocatorRegistryHolder {
            private static final LocatorRegistry INSTANCE = new LocatorRegistry();
        }

        public static LocatorRegistry getInstance() {
            return LocatorRegistryHolder.INSTANCE;
        }
    }

}
