package io.magentys.cinnamon.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.magentys.cinnamon.conf.Env;

public final class CinnamonModule extends AbstractModule {

    @Override
    public void configure() {
        install(new GuiceModuleAggregator.Builder().withPackages("io.magentys.cinnamon").build());
    }

    @Provides
    @Singleton
    Env provideEnv() {
        return Env.INSTANCE;
    }

}
