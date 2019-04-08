package io.magentys.cinnamon.webdriver;

import java.time.Duration;

@FunctionalInterface
public interface Timeout {

    Duration getDuration();

    default long getSeconds() {
        return getDuration().getSeconds();
    }

    default long getMillis() {
        return getDuration().toMillis();
    }
}