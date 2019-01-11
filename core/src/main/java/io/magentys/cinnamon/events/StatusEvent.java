package io.magentys.cinnamon.events;

public interface StatusEvent {
    boolean isFailed();

    cucumber.api.Result.Type getStatus();
}
