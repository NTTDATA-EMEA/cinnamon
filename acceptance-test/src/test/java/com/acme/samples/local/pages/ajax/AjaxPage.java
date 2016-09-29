package com.acme.samples.local.pages.ajax;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

import static io.magentys.cinnamon.webdriver.Browser.waitUntil;
import static io.magentys.cinnamon.webdriver.Timeouts.timeoutInSeconds;
import static io.magentys.cinnamon.webdriver.conditions.Conditions.ajaxFinished;

public class AjaxPage {

    @FindBy(id = "ajax_button")
    private PageElement ajaxButton;

    @FindBy(id = "latent_text")
    private PageElement latentText;

    public void clickAjaxButton() {
        ajaxButton.click();
    }

    public String waitForResponseText(long seconds) {
        try {
            waitUntil(ajaxFinished, timeoutInSeconds(seconds));
            return latentText.text();
        } catch (TimeoutException e) {
            return null;
        }
    }
}
