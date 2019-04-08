package io.magentys.cinnamon.webdriver;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static io.magentys.cinnamon.webdriver.WebDriverUtils.toChronoUnit;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

public final class Timeouts {

    // Suppresses default constructor, ensuring non-instantiability.
    private Timeouts() {
    }

    public static OffsetsTimeout defaultTimeout() {
        return new DefaultTimeout();
    }

    public static Timeout timeoutInSeconds(final long time) {
        return timeoutIn(time, SECONDS);
    }

    public static Timeout timeoutInMillis(final long time) {
        return timeoutIn(time, MILLIS);
    }

    /**
     * Sets how long to wait for an evaluated condition to become true.
     *
     * @param time The timeout duration.
     * @param unit The unit of time.
     * @return Timeout object.
     * @deprecated use {@link #timeoutIn(long, ChronoUnit)}
     */
    @Deprecated
    public static Timeout timeoutIn(final long time, final TimeUnit unit) {
        return timeoutIn(time, toChronoUnit(unit));
    }

    /**
     * Sets how long to wait for an evaluated condition to become true.
     *
     * @param time The timeout duration.
     * @param unit The unit of time.
     * @return Timeout object.
     */
    public static Timeout timeoutIn(final long time, final ChronoUnit unit) {
        return () -> Duration.of(time, unit);
    }
}