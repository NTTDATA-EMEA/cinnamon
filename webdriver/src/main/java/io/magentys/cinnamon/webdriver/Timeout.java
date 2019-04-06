package io.magentys.cinnamon.webdriver;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

@FunctionalInterface
public interface Timeout {

    Duration getDuration();

    default long getSeconds() {
        return getDuration().get(SECONDS);
    }

    default long getMillis() {
        return getDuration().get(MILLIS);
    }
}