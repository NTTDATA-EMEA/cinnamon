package com.acme.samples.local.pages.input;

@SuppressWarnings("unused")
public class DisplayedElement {

    private final String locator;
    private final String nameAttribute;
    private final String value;

    public DisplayedElement(final String locator, final String nameAttribute, final String value) {
        super();
        this.locator = locator;
        this.nameAttribute = nameAttribute;
        this.value = value;
    }
}
