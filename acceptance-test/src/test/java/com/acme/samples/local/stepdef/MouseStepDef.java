package com.acme.samples.local.stepdef;

import com.acme.samples.local.pages.mouse.MousePage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fest.assertions.api.Assertions;

import javax.inject.Inject;

public class MouseStepDef {

    private final MousePage page;

    @Inject
    public MouseStepDef(final MousePage page) {
        this.page = page;
    }

    @When("I choose to hover over element {string}")
    public void i_choose_to_hover_over_element(final String elementId) throws Throwable {
        page.hoverOver(elementId);
    }

    @Then("I shall see the text {string}")
    public void i_shall_see_the_text(final String expectedText) throws Throwable {
        Assertions.assertThat(page.containsText(expectedText)).isTrue();
    }
}
