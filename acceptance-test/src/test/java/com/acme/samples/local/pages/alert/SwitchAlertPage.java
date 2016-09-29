package com.acme.samples.local.pages.alert;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

import static io.magentys.cinnamon.webdriver.Browser.alert;

public class SwitchAlertPage {

    @FindByKey("alert.button")
    private PageElement alertButton;

    @FindByKey("slow_loading_alert.button")
    private PageElement slowLoadingAlertButton;

    @FindByKey("alert_confirm.button")
    private PageElement alertConfirmButton;

    @FindByKey("alert_prompt.button")
    private PageElement alertPromptButton;

    @FindByKey("alert_return_result.text")
    private PageElement alertReturnResult;

    public void createAlert() {
        alertButton.click();
    }

    public void createSlowLoadingAlert() {
        slowLoadingAlertButton.click();
    }

    public void createConfirmAlert() {
        alertConfirmButton.click();
    }

    public void createPromptAlert() {
        alertPromptButton.click();
    }

    public void acceptAlert() {
        alert().accept();
    }

    public void dismissAlert() {
        alert().dismiss();
    }

    public String getAlertReturnValue() {
        return alertReturnResult.text();
    }

    public void inputAlertText(String alertText) {
        alert().sendKeys(alertText);
    }
}