package com.acme.samples.google.pages.result;

import java.util.List;

public interface ResultsPage {

    List<String> checkResults();

    boolean waitUntilLoaded();
}
