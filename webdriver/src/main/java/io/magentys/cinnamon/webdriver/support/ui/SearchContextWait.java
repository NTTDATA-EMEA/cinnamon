package io.magentys.cinnamon.webdriver.support.ui;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * A specialisation of {@link FluentWait} that uses SearchContext instances.
 */
public class SearchContextWait extends FluentWait<SearchContext> {

    public final static long DEFAULT_SLEEP_TIMEOUT = 500;

    protected SearchContextWait(final SearchContext input, final Clock clock, final Sleeper sleeper, final long timeout,
            final TimeUnit timeoutTimeUnit, final long sleep, final TimeUnit sleepTimeUnit) {
        super(input, clock, sleeper);
        withTimeout(timeout, timeoutTimeUnit);
        pollingEvery(sleep, sleepTimeUnit);
        ignoring(NotFoundException.class);
    }

    public SearchContextWait(final SearchContext input, final long timeoutMillis) {
        this(input, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeoutMillis, TimeUnit.MILLISECONDS, DEFAULT_SLEEP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public SearchContextWait(final SearchContext input, final long timeoutMillis, final long sleepMillis) {
        this(input, new SystemClock(), Sleeper.SYSTEM_SLEEPER, timeoutMillis, TimeUnit.MILLISECONDS, sleepMillis, TimeUnit.MILLISECONDS);
    }
}
