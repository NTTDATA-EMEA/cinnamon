package com.acme.samples.google.pages.home;

import com.acme.samples.google.context.GoogleContext;
import io.magentys.cinnamon.conf.Env;
import io.magentys.cinnamon.webdriver.Browser;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

public abstract class AbstractHomePage implements HomePage {

    protected static final String GOOGLE_URL = "google-url";

    @FindByKey("google.home-page.search-button")
    protected PageElement searchButton;
    @FindByKey("google.home-page.search-input")
    protected PageElement searchInput;

    protected final GoogleContext context;
    protected final Env env;

    public AbstractHomePage(final Env env, final GoogleContext context) {
        this.context = context;
        this.env = env;
    }

    @Override
    public void open() {
        Browser.open(env.config.getString(GOOGLE_URL));
    }

    @Override
    public abstract boolean waitUntilOpened();

    @Override
    public abstract void enterSearchTerm(String searchTerm);
}
