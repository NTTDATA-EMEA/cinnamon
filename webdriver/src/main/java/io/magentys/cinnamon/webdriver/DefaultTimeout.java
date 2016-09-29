package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.support.ui.Duration;

import java.util.concurrent.TimeUnit;

import static io.magentys.cinnamon.webdriver.Timeouts.timeoutIn;

public class DefaultTimeout implements OffsetsTimeout {

    public static final long DEFAULT_TIMEOUT_SECS = Long.getLong("webdriver.wait.timeout", 5);
    private final Duration duration;

    public DefaultTimeout() {
        this.duration = new Duration(DEFAULT_TIMEOUT_SECS, TimeUnit.SECONDS);
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public Timeout plusMillis(long seconds) {
        return plus(seconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public Timeout plusSeconds(long seconds) {
        return plus(seconds, TimeUnit.SECONDS);
    }

    @Override
    public Timeout plus(long time, TimeUnit unit) {
        return timeoutIn(duration.in(unit) + time, unit);
    }

    @Override
    public Timeout minusMillis(long seconds) {
        return minus(seconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public Timeout minusSeconds(long seconds) {
        return minus(seconds, TimeUnit.SECONDS);
    }

    @Override
    public Timeout minus(long time, TimeUnit unit) {
        return timeoutIn(time > duration.in(unit) ? 0 : duration.in(unit) - time, unit);
    }
}