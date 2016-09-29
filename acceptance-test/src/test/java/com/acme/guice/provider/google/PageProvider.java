package com.acme.guice.provider.google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class PageProvider {
    protected final WebDriver webDriver;

    public PageProvider(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    //Is this ever used?
    //We should expose if it is mobile from the webdriver factory
    //based on the capabilities including the emulations
    //and somehow make it available to the users.
    protected boolean isMobile() {
        String browserName = ((RemoteWebDriver) webDriver).getCapabilities().getBrowserName();
        String platformName = ((RemoteWebDriver) webDriver).getCapabilities().getPlatform().name();
        return "ipad".equalsIgnoreCase(browserName) ||
                "iphone".equalsIgnoreCase(browserName) ||
                "android".equalsIgnoreCase(browserName) ||
                "android".equalsIgnoreCase(platformName);
    }
}
