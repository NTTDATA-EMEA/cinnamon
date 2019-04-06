package io.magentys.cinnamon.webdriver.support.ui;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;

/**
 * A specialisation of {@link FluentWait} that uses SearchContext instances.
 */
public class SearchContextWait extends FluentWait<SearchContext> {

    public final static Duration DEFAULT_SLEEP_TIMEOUT = Duration.ofMillis(500L);

    protected SearchContextWait(final SearchContext input, final Clock clock, final Sleeper sleeper, final Duration timeout, final Duration sleep) {
        super(input, clock, sleeper);
        withTimeout(timeout);
        pollingEvery(sleep);
        ignoring(NotFoundException.class);
    }

    public SearchContextWait(final SearchContext input, final long timeoutMillis) {
        this(input, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER, Duration.ofMillis(timeoutMillis), DEFAULT_SLEEP_TIMEOUT);
    }

    public SearchContextWait(final SearchContext input, final long timeoutMillis, final long sleepMillis) {
        this(input, Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER, Duration.ofMillis(timeoutMillis), Duration.ofMillis(sleepMillis));
    }
}
