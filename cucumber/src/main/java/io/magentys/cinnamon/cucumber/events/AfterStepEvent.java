package io.magentys.cinnamon.cucumber.events;

import io.magentys.cinnamon.events.StepEvent;

public class AfterStepEvent implements StepEvent {
    private final String name;

    public AfterStepEvent(String name) {
    this.name=name;
    }

    @Override
    public String getName() {
        return name;
    }
}
