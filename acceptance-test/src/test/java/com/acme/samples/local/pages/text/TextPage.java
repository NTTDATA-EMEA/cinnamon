package com.acme.samples.local.pages.text;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;

public class TextPage {

    @FindByKey("text.div")
    private PageElement div;

    @FindByKey("text.hidden-by-css-div")
    private PageElement hiddenByCssDiv;

    @FindByKey("text.hidden-by-attribute-div")
    private PageElement hiddenByAttributeDiv;

    @FindByKey("text.span-within-div")
    private PageElement spanWithinDiv;

    @FindByKey("text.hidden-by-css-span-within-div")
    private PageElement hiddenByCssSpanWithinDiv;

    @FindByKey("text.hidden-by-attribute-span-within-div")
    private PageElement hiddenByAttributeSpanWithinDiv;

    @FindByKey("text.input")
    private PageElement input;

    @FindByKey("text.hidden-by-css-input")
    private PageElement hiddenByCssInput;

    @FindByKey("text.hidden-by-attribute-input")
    private PageElement hiddenByAttributeInput;

    @FindByKey("text.textarea")
    private PageElement textarea;

    @FindByKey("text.hidden-by-css-textarea")
    private PageElement hiddenByCssTextarea;

    @FindByKey("text.hidden-by-attribute-textarea")
    private PageElement hiddenByAttributeTextarea;

    public String getDivText() {
        return div.text();
    }

    public String getHiddenByCssDivText() {
        return hiddenByCssDiv.text();
    }

    public String getHiddenByAttributeDivText() {
        return hiddenByAttributeDiv.text();
    }

    public String getSpanWithinDivText() {
        return spanWithinDiv.text();
    }

    public String getHiddenByCssSpanWithinDivText() {
        return hiddenByCssSpanWithinDiv.text();
    }

    public String getHiddenByAttributeSpanWithinDivText() {
        return hiddenByAttributeSpanWithinDiv.text();
    }

    public void enterInputText(String text) {
        input.replaceText(text);
    }

    public String getInputText() {
        return input.text();
    }

    public String getHiddenByCssInputText() {
        return hiddenByCssInput.text();
    }

    public String getHiddenByAttributeInputText() {
        return hiddenByAttributeInput.text();
    }

    public void enterTextareaText(String text) {
        textarea.replaceText(text);
    }

    public String getTextareaText() {
        return textarea.text();
    }

    public String getHiddenByCssTextareaText() {
        return hiddenByCssTextarea.text();
    }

    public String getHiddenByAttributeTextareaText() {
        return hiddenByAttributeTextarea.text();
    }
}