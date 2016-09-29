package com.acme.samples.local.stepdef;

import javax.inject.Inject;

import org.junit.Assert;

import com.acme.samples.local.pages.text.TextPage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TextStepDef {
    private final TextPage textPage;

    @Inject
    public TextStepDef(final TextPage textPage) {
        this.textPage = textPage;
    }

    @When("^I enter text \"(.*)\" into \"(.*)\"$")
    public void i_enter_text_into(String text, String elementType) throws Throwable {
        switch (elementType) {
        case "input":
            textPage.enterInputText(text);
            break;
        case "textarea":
            textPage.enterTextareaText(text);
            break;
        default:
            Assert.fail("Unsupported element type: " + elementType);
        }
    }

    @Then("^inspecting text of the \"(.*)\" element should return \"(.*)\"$")
    public void i_choose_to_inspect_element_text(String elementType, String expectedText) throws Throwable {
        String actualText = null;
        switch (elementType) {
        case "div":
            actualText = textPage.getDivText();
            break;
        case "hidden by CSS div":
            actualText = textPage.getHiddenByCssDivText();
            break;
        case "hidden by attribute div":
            actualText = textPage.getHiddenByAttributeDivText();
            break;
        case "span within div":
            actualText = textPage.getSpanWithinDivText();
            break;
        case "hidden by CSS span within div":
            actualText = textPage.getHiddenByCssSpanWithinDivText();
            break;
        case "hidden by attribute span within div":
            actualText = textPage.getHiddenByAttributeSpanWithinDivText();
            break;
        case "input":
            actualText = textPage.getInputText();
            break;
        case "hidden by CSS input":
            actualText = textPage.getHiddenByCssInputText();
            break;
        case "hidden by attribute input":
            actualText = textPage.getHiddenByAttributeInputText();
            break;
        case "textarea":
            actualText = textPage.getTextareaText();
            break;
        case "hidden by CSS textarea":
            actualText = textPage.getHiddenByCssTextareaText();
            break;
        case "hidden by attribute textarea":
            actualText = textPage.getHiddenByAttributeTextareaText();
            break;
        default:
            Assert.fail("Unsupported element type: " + elementType);
        }
        Assert.assertEquals(expectedText, actualText);
    }
}
