package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.support.ui.Duration;

import java.util.concurrent.TimeUnit;

public final class Timeouts {

    // Suppresses default constructor, ensuring non-instantiability.
    private Timeouts() {}

    public static OffsetsTimeout defaultTimeout() {
        return new DefaultTimeout();
    }

    public static Timeout timeoutInSeconds(final long time) {
        return timeoutIn(time, TimeUnit.SECONDS);
    }

    public static Timeout timeoutInMillis(final long time) {
        return timeoutIn(time, TimeUnit.MILLISECONDS);
    }

    public static Timeout timeoutIn(final long time, final TimeUnit unit) {
        return () -> new Duration(time, unit);
    }
}