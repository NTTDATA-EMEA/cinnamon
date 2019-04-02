package io.magentys.cinnamon.events;

public interface StatusEvent {
    boolean isFailed();

    String getStatus();
}
