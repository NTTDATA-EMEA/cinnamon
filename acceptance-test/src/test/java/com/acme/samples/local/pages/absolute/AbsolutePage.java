package com.acme.samples.local.pages.absolute;

import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.attributeEquals;

public class AbsolutePage {

    @FindByKey("absolute.button")
    private PageElementCollection buttons;

    public void clickBtnWithOffset(final String buttonId, final int x, final int y) {
        final PageElement btn = buttons.first(attributeEquals("id", buttonId));
        btn.byOffset(x, y).click();
    }
}