package io.magentys.cinnamon.events;

public interface BeforeHookEvent {

    boolean isFailed();

    String getStatus();

    String getErrorMessage();

    Throwable getError();
}
