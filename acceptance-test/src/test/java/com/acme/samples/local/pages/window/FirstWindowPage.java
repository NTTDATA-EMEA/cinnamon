package com.acme.samples.local.pages.window;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

import static io.magentys.cinnamon.webdriver.Browser.switchTo;

public class FirstWindowPage {

    @FindByKey("last_window.link")
    private PageElement lastWindowLink;

    @FindByKey("second_window.link")
    private PageElement secondWindowLink;

    @FindByKey("first_window.text")
    private PageElement textElement;

    public void clickLastWindowLink() {
        lastWindowLink.click();
    }

    public void clickSecondWindowLink() {
        secondWindowLink.click();
    }

    public void switchToNew() {
        switchTo().newWindow();
    }

    public void switchToFirst() {
        switchTo().window().first();
    }

    public String getTextElementText() {
        return textElement.text();
    }
}