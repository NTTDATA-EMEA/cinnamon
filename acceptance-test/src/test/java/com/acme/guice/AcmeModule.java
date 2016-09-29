package com.acme.guice;

import com.acme.guice.provider.google.HomePageProvider;
import com.acme.guice.provider.google.ResultsPageProvider;
import com.acme.samples.google.context.GoogleContext;
import com.acme.samples.google.pages.home.HomePage;
import com.acme.samples.google.pages.result.ResultsPage;
import com.acme.samples.local.context.LocalContext;
import com.google.inject.AbstractModule;

import cucumber.api.guice.CucumberScopes;

public final class AcmeModule extends AbstractModule {
    @Override
    public void configure() {
        try {
            // Bindings for classes that are shared for the lifetime of the
            // scenario.
            bind(GoogleContext.class).in(CucumberScopes.SCENARIO);
            bind(LocalContext.class).in(CucumberScopes.SCENARIO);
            bind(HomePage.class).toProvider(HomePageProvider.class).in(CucumberScopes.SCENARIO);
            bind(ResultsPage.class).toProvider(ResultsPageProvider.class).in(CucumberScopes.SCENARIO);
        } catch (Exception e) {
            addError(e.getMessage());
        }
    }
}
