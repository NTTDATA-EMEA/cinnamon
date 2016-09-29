package com.acme.samples.google.pages.result;

import com.acme.samples.google.context.GoogleContext;

import javax.inject.Inject;

public class MobileResultsPage extends AbstractResultsPage {

    @Inject
    public MobileResultsPage(final GoogleContext context) {
        super(context);
    }
}
