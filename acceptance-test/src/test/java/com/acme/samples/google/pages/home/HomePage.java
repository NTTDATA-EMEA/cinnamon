package com.acme.samples.google.pages.home;

public interface HomePage {

    void open();

    boolean waitUntilOpened();

    void enterSearchTerm(String searchTerm);
}
