package com.acme.samples.local.stepdef;

import com.acme.samples.local.pages.ajax.AjaxPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import javax.inject.Inject;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class AjaxStepDef {

    private final AjaxPage ajaxPage;

    @Inject
    public AjaxStepDef(final AjaxPage ajaxPage) {
        this.ajaxPage = ajaxPage;
    }

    @Given("I have triggered a high latency ajax call")
    public void i_have_triggered_a_high_latency_ajax_call() throws Throwable {
        ajaxPage.clickAjaxButton();
    }

    @Then("I should see response text if the timeout for ajax to finish is set to {long} seconds")
    public void i_should_see_response_text(long seconds) throws Throwable {
        assertThat(ajaxPage.waitForResponseText(seconds), equalTo("Some text response"));
    }

    @Then("I should not see response text if the timeout for ajax to finish is set to {long} seconds")
    public void i_should_not_see_response_text(long seconds) throws Throwable {
        assertThat(ajaxPage.waitForResponseText(seconds), nullValue());
    }

}
