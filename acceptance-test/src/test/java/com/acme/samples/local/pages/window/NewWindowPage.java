package com.acme.samples.local.pages.window;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

import static io.magentys.cinnamon.webdriver.Browser.switchTo;

public class NewWindowPage {

    @FindByKey("last_window.text")
    private PageElement textElement;

    public void switchToLast() {
        switchTo().window().last();
    }

    public void closeNew() {
        switchTo().newWindow().close();
        //TODO WebDriver does not automatically switch to the parent window after close. This needs to be handled.
    }

    public String getTextElementText() {
        return textElement.text();
    }

    public void switchToByTitle(String title) {
        switchTo().window().byTitle(title);
    }

    public void switchToByPartialTitle(String partialTitle) {
        switchTo().window().byPartialTitle(partialTitle);
    }
}
