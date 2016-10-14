package com.acme.samples.google.pages.home;

import com.acme.samples.google.context.GoogleContext;
import io.magentys.cinnamon.conf.Env;

import javax.inject.Inject;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.*;

public class DesktopHomePage extends AbstractHomePage {

    @Inject
    public DesktopHomePage(final Env env, final GoogleContext context) {
        super(env, context);
    }

    @Override
    public boolean waitUntilOpened() {
        return searchButton.waitUntil(displayed.and(textContainsIgnoreCase("Search"))).isPresent();
    }

    @Override
    public void enterSearchTerm(final String searchTerm) {
        // Enter the search term.
        searchInput.waitUntil(clickable.and(positionUnchanged(500))).replaceText(searchTerm);
        context.setSearchFilter(searchTerm);
    }
}