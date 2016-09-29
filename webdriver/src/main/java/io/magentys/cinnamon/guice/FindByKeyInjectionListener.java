package io.magentys.cinnamon.guice;

import io.magentys.cinnamon.webdriver.support.PageFactory;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.inject.spi.InjectionListener;

public class FindByKeyInjectionListener implements InjectionListener<Object> {

    @Inject
    private Provider<PageFactory> pageFactory;

    @Override
    public void afterInjection(Object injectee) {
        pageFactory.get().initElements(injectee);
    }

}
