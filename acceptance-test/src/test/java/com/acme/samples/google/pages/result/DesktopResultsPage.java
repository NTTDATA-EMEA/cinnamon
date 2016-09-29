package com.acme.samples.google.pages.result;

import com.acme.samples.google.context.GoogleContext;

import javax.inject.Inject;

public class DesktopResultsPage extends AbstractResultsPage {

    @Inject
    public DesktopResultsPage(final GoogleContext context) {
        super(context);
    }
}
