package com.acme.samples.google.pages.result;

import com.acme.samples.google.context.GoogleContext;
import io.magentys.cinnamon.webdriver.collections.PageElementCollection;
import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.magentys.cinnamon.webdriver.Timeouts.defaultTimeout;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.displayed;
import static io.magentys.cinnamon.webdriver.conditions.ElementConditions.enabled;

public abstract class AbstractResultsPage implements ResultsPage {

    private static final String SEARCH_RESULT_LOCATOR = "google.results-page.search-result";
    private static final String SEARCH_RESULT_LINKS_LOCATOR = "google.results-page.search-result-links";

    protected final GoogleContext context;

    public AbstractResultsPage(final GoogleContext context) {
        this.context = context;
    }

    @FindByKey(value = SEARCH_RESULT_LOCATOR)
    private PageElement searchResult;

    @FindByKey(value = SEARCH_RESULT_LINKS_LOCATOR)
    private PageElementCollection searchResultLinks;

    @Override
    public List<String> checkResults() {
        final List<String> failures = new ArrayList<>();
        final PageElementCollection results = searchResultLinks.filter(displayed);
        for (final WebElement result : results.getWrappedElements()) {
            failures.addAll(matchResult(result));
        }
        return failures;
    }

    private Collection<? extends String> matchResult(final WebElement result) {
        final List<String> failures = new ArrayList<>();
        try {
            final String searchResultText = result.getText();
            if (!StringUtils.containsIgnoreCase(searchResultText.replaceAll("\\s+", ""), context.getSearchFilter())) {
                failures.add(
                        "The search result [" + searchResultText + "] is not relevant for the search filter [" + context.getSearchFilter() + "].");
            }
        } catch (final Exception e) {
            failures.add(String.valueOf(e));
        }
        return failures;
    }

    @Override
    public boolean waitUntilLoaded() {
        return searchResult.waitUntil(displayed.and(enabled), defaultTimeout().minusMillis(4000)).isPresent();
    }
}
