package com.acme.guice;

import com.acme.guice.provider.google.HomePageProvider;
import com.acme.guice.provider.google.ResultsPageProvider;
import com.acme.samples.google.context.GoogleContext;
import com.acme.samples.google.pages.home.HomePage;
import com.acme.samples.google.pages.result.ResultsPage;
import com.acme.samples.local.context.LocalContext;
import com.google.inject.AbstractModule;
import cucumber.runtime.java.guice.ScenarioScoped;

public final class AcmeModule extends AbstractModule {

    @Override
    public void configure() {
        try {
            // Bindings for classes that are shared for the lifetime of the
            // scenario.
            bind(GoogleContext.class).in(ScenarioScoped.class);
            bind(LocalContext.class).in(ScenarioScoped.class);
            bind(HomePage.class).toProvider(HomePageProvider.class);
            bind(ResultsPage.class).toProvider(ResultsPageProvider.class);
        } catch (Exception e) {
            addError(e.getMessage());
        }
    }
}
