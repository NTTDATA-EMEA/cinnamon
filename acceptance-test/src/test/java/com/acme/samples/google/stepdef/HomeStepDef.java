package com.acme.samples.google.stepdef;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.acme.samples.google.pages.home.HomePage;
import com.google.inject.Inject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomeStepDef {
    private final HomePage homePage;

    @Inject
    public HomeStepDef(final HomePage homePage) {
        this.homePage = homePage;
    }

    @Given("^I have opened google$")
    public void I_have_opened_google() throws Throwable {
        homePage.open();
    }

    @When("^I search for \"(.*?)\"$")
    public void I_search_for_term(final String searchTerm) throws Throwable {
        try {
            homePage.enterSearchTerm(searchTerm);
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @When("^I search for:$")
    public void I_search_for(final List<Map<String, String>> rows) throws Throwable {
        try {
            homePage.enterSearchTerm(rows.get(0).get("term"));
        } catch (final Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Then("^I should see the home page$")
    public void I_should_see_the_home_page() throws Throwable {
        Assert.assertTrue("The Home page was not displayed.", homePage.waitUntilOpened());
    }
}
