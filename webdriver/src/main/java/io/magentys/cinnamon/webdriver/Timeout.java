package io.magentys.cinnamon.webdriver;

import org.openqa.selenium.support.ui.Duration;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface Timeout {

    Duration getDuration();

    default long getSeconds() {
        return getDuration().in(TimeUnit.SECONDS);
    }

    default long getMillis() {
        return getDuration().in(TimeUnit.MILLISECONDS);
    }
}