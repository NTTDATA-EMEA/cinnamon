package com.acme.samples.local.stepdef;

import com.acme.samples.local.pages.window.FirstWindowPage;
import com.acme.samples.local.pages.window.NewWindowPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.inject.Inject;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class SwitchWindowStepDef {

    private final FirstWindowPage firstWindowPage;
    private final NewWindowPage newWindowPage;

    @Inject
    public SwitchWindowStepDef(final FirstWindowPage firstWindowPage, final NewWindowPage newWindowPage) {
        this.firstWindowPage = firstWindowPage;
        this.newWindowPage = newWindowPage;
    }

    @Given("a new window has been opened")
    public void a_new_window_has_been_opened() throws Throwable {
        firstWindowPage.clickLastWindowLink();
    }

    @Given("two new windows have been opened")
    public void two_new_windows_have_been_opened() throws Throwable {
        firstWindowPage.clickSecondWindowLink();
        firstWindowPage.clickLastWindowLink();
    }

    @When("I switch to the new(est) window")
    public void i_switch_to_the_new_window() throws Throwable {
        firstWindowPage.switchToNew();
    }

    @When("I close the new window")
    public void i_close_the_new_window() throws Throwable {
        newWindowPage.closeNew();
    }

    @When("I switch to the {nth} window")
    public void i_switch_to_the_window(String nth) throws Throwable {
        switch (nth.trim()) {
        case "first":
            firstWindowPage.switchToFirst();
            break;
        case "last":
            newWindowPage.switchToLast();
            break;
        default:
            throw new RuntimeException(String.format("The case %s is not implemented yet", nth));
        }
    }

    @When("I switch to the window with title {string}")
    public void i_switch_to_the_window_with_title(String title) throws Throwable {
        newWindowPage.switchToByTitle(title);
    }

    @When("I switch to the window with partial title {string}")
    public void i_switch_focus_to_the_window_with_title_containing(String partialTitle) throws Throwable {
        newWindowPage.switchToByPartialTitle(partialTitle);
    }

    @Then("I should see the text {string} in the new window")
    public void i_should_see_the_text_in_the_new_window(String text) throws Throwable {
        assertThat(newWindowPage.getTextElementText(), containsString(text));
    }

    @Then("I should see the text {string} in the first window")
    public void i_should_see_the_text_in_the_first_window(String text) throws Throwable {
        assertThat(firstWindowPage.getTextElementText(), containsString(text));
    }
}