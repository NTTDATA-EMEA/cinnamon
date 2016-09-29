package com.acme.guice.provider.google;

import org.openqa.selenium.WebDriver;

import com.acme.samples.google.pages.result.DesktopResultsPage;
import com.acme.samples.google.pages.result.MobileResultsPage;
import com.acme.samples.google.pages.result.ResultsPage;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class ResultsPageProvider extends PageProvider implements Provider<ResultsPage> {
    @Inject
    private DesktopResultsPage desktopResultsPage;
    @Inject
    private MobileResultsPage mobileResultsPage;

    @Inject
    public ResultsPageProvider(WebDriver webDriver) {
        super(webDriver);
    }

    public ResultsPage get() {
        return isMobile() ? mobileResultsPage : desktopResultsPage;
    }
}
