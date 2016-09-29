package io.magentys.cinnamon.eventbus;

import com.google.common.eventbus.EventBus;

public class EventBusContainer {

    private static final ThreadLocal<EventBus> eventBus = new ThreadLocal<>();

    public static EventBus getEventBus() {
        if (eventBus.get() == null) {
            eventBus.set(new EventBus());
        }
        return eventBus.get();
    }

    public static void removeEventBus() {
        eventBus.remove();
    }
}
