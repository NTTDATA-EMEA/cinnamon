package com.acme.samples.google.pages.home;

import com.acme.samples.google.context.GoogleContext;
import io.magentys.cinnamon.conf.Env;

import javax.inject.Inject;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.displayed;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.enabled;

public class MobileHomePage extends AbstractHomePage {

    @Inject
    public MobileHomePage(final Env env, final GoogleContext context) {
        super(env, context);
    }

    @Override
    public boolean waitUntilOpened() {
        return searchButton.waitUntil(displayed) != null;
    }

    @Override
    public void enterSearchTerm(final String searchTerm) {
        // Enter the search term.
        searchInput.waitUntil(displayed.and(enabled)).replaceText(searchTerm);
        // Click the search button.
        searchButton.waitUntil(displayed.and(enabled)).click();
    }
}