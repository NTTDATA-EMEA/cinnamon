package com.acme.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;
import io.magentys.cinnamon.guice.CinnamonModule;

public class GuiceInjectorSource implements InjectorSource {

    @Override
    public Injector getInjector() {
        return Guice.createInjector(CucumberModules.createScenarioModule(), new CinnamonModule(), new AcmeModule());
    }
}
