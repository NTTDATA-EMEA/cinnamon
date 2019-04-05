package com.acme.samples.google.stepdef;

import com.acme.samples.google.pages.result.ResultsPage;
import com.google.inject.Inject;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class ResultsStepDef {
    private final ResultsPage resultsPage;

    @Inject
    public ResultsStepDef(final ResultsPage resultsPage) {
        this.resultsPage = resultsPage;
    }

    @Then("I should see the results page")
    public void I_should_see_the_results_page() {
        Assert.assertTrue("The results page was not displayed.", resultsPage.waitUntilLoaded());
    }

    @Then("I should not see the results page")
    public void I_should_not_see_the_results_page() {
        Assert.assertFalse("The results page was displayed.", resultsPage.waitUntilLoaded());
    }

    @Then("I should see only relevant results")
    public void I_should_see_only_relevant_results() {
        List<String> failures = resultsPage.checkResults();
        Assert.assertTrue("There are results that do not match the search filter [" + Arrays.asList(failures.toArray()) + "].", failures.isEmpty());
    }
}
