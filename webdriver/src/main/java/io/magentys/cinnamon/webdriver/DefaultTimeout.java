package io.magentys.cinnamon.webdriver;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static io.magentys.cinnamon.webdriver.Timeouts.timeoutIn;
import static io.magentys.cinnamon.webdriver.WebDriverUtils.toChronoUnit;

public class DefaultTimeout implements OffsetsTimeout {

    public static final long DEFAULT_TIMEOUT_SECS = Long.getLong("webdriver.wait.timeout", 5);
    private final Duration duration;

    public DefaultTimeout() {
        this.duration = Duration.ofSeconds(DEFAULT_TIMEOUT_SECS);
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
    @Deprecated
    public Timeout plus(long time, TimeUnit unit) {
        return plus(time, toChronoUnit(unit));
    }

    @Override
    public Timeout plus(long time, ChronoUnit unit) {
        return timeoutIn(duration.get(unit) + time, unit);
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
    @Deprecated
    public Timeout minus(long time, TimeUnit unit) {
        return minus(time, toChronoUnit(unit));
    }

    @Override
    public Timeout minus(long time, ChronoUnit unit) {
        return timeoutIn(time > duration.get(unit) ? 0 : duration.get(unit) - time, unit);
    }
}