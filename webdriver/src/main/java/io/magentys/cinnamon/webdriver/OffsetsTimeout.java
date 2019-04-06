package io.magentys.cinnamon.webdriver;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public interface OffsetsTimeout extends Timeout {

    Timeout plus(long time, ChronoUnit unit);

    @Deprecated
    Timeout plus(long time, TimeUnit unit);

    Timeout plusMillis(long millis);

    Timeout plusSeconds(long seconds);

    Timeout minus(long time, ChronoUnit unit);

    @Deprecated
    Timeout minus(long time, TimeUnit unit);

    Timeout minusMillis(long millis);

    Timeout minusSeconds(long seconds);
}