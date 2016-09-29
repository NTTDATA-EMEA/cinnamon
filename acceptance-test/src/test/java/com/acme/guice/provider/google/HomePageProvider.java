package com.acme.guice.provider.google;

import org.openqa.selenium.WebDriver;

import com.acme.samples.google.pages.home.DesktopHomePage;
import com.acme.samples.google.pages.home.HomePage;
import com.acme.samples.google.pages.home.MobileHomePage;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class HomePageProvider extends PageProvider implements Provider<HomePage> {
    @Inject
    private DesktopHomePage desktopHomePage;
    @Inject
    private MobileHomePage mobileHomePage;

    @Inject
    public HomePageProvider(WebDriver webDriver) {
        super(webDriver);
    }

    public HomePage get() {
        return isMobile() ? mobileHomePage : desktopHomePage;
    }
}
