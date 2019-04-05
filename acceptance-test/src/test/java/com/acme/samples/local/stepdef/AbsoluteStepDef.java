package com.acme.samples.local.stepdef;

import com.acme.samples.local.pages.absolute.AbsolutePage;
import cucumber.api.java.en.When;

import javax.inject.Inject;

public class AbsoluteStepDef {

    private final AbsolutePage page;

    @Inject
    public AbsoluteStepDef(final AbsolutePage page) {
        this.page = page;
    }

    @When("I choose to click button {string} with offset \\({int},{int})")
    public void i_choose_to_click_button_with_offset(final String buttonId, final int x, final int y) throws Throwable {
        page.clickBtnWithOffset(buttonId, x, y);
    }
}
