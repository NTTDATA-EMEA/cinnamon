package com.acme.samples.local.pages.select;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

public class SelectPage {

    @FindByKey("select.select")
    private PageElement select;
    @FindByKey("select.content")
    private PageElement content;

    public void selectByValue(final String val) {
        select.select().byValue(val);
    }

    public void selectByText(final String text) {
        select.select().byVisibleText(text);
    }

    public void selectByTextContains(final String text) {
        select.select().byVisibleTextContains(text);
    }

    public void selectByIndex(final int idx) {
        select.select().byIndex(idx);
    }

    public boolean contains(final String text) {
        return content.text().contains(text);
    }
}
