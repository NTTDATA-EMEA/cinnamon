package com.acme.samples.local.stepdef;

import com.acme.samples.local.context.LocalContext;
import com.acme.samples.local.pages.alert.SwitchAlertPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.UnhandledAlertException;

import javax.inject.Inject;

public class SwitchToAlertStepDef {

    private final SwitchAlertPage alertPage;
    private final LocalContext context;

    @Inject
    public SwitchToAlertStepDef(final SwitchAlertPage alertPage, final LocalContext context) {
        this.alertPage = alertPage;
        this.context = context;
    }

    @Given("^a new slow loading alert has been created$")
    public void a_new_slow_loading_alert_has_been_created() {
        alertPage.createSlowLoadingAlert();
    }

    @Given("^a new alert has been created$")
    public void a_new_alert_has_been_created() {
        alertPage.createAlert();
    }

    @Given("^a new confirm alert has been created$")
    public void a_new_confirm_alert_has_been_created() {
        alertPage.createConfirmAlert();
    }

    @Given("^a new prompt alert has been created$")
    public void a_new_prompt_alert_has_been_created() {
        alertPage.createPromptAlert();
    }

    @When("^I accept the alert$")
    public void i_accept_the_alert() {
        try {
            alertPage.acceptAlert();
        } catch (UnhandledAlertException e) {
            context.setAlertPresent(true);
        }
    }

    @When("^I dismiss the alert$")
    public void i_dismiss_the_alert() {
        try {
            alertPage.dismissAlert();
        } catch (UnhandledAlertException e) {
            context.setAlertPresent(true);
        }
    }

    @Then("^the alert should be closed$")
    public void the_alert_should_be_closed() throws Throwable {
        Assert.assertFalse("Alert is present", context.isAlertPresent());
    }

    @When("^I enter the text \"([^\"]*)\" into the alert$")
    public void i_enter_the_text_into_the_alert(String alertTest) {
        alertPage.inputAlertText(alertTest);
    }

    @When("^the alert return value should should equal \"([^\"]*)\"$")
    public void the_alert_return_value_should_should_equal(String returnValue) {
        Assert.assertEquals(returnValue, alertPage.getAlertReturnValue());
    }

}
