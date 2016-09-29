package com.acme.samples.local.stepdef;

import javax.inject.Inject;

import org.junit.Assert;

import com.acme.samples.local.pages.select.SelectPage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SelectStepDef {

    private final SelectPage page;

    @Inject
    public SelectStepDef(final SelectPage page) {
        this.page = page;
    }

    @When("^I choose value (\\d+)$")
    public void i_choose_value(final int val) throws Throwable {
        page.selectByValue(String.valueOf(val));
    }

    @When("^I choose text contains \"(.*?)\"$")
    public void i_choose_text_contains(final String val) throws Throwable {
        page.selectByTextContains(String.valueOf(val));
    }

    @Then("^I should see \"(.*?)\"$")
    public void i_should_see(final String text) throws Throwable {
        Assert.assertTrue(page.contains(text));
    }

    @When("^I choose text \"(.*?)\"$")
    public void i_choose_text(final String text) throws Throwable {
        page.selectByText(text);
    }

    @When("^I choose index (\\d+)$")
    public void i_choose_index(final int idx) throws Throwable {
        page.selectByIndex(idx);
    }
}
