package io.magentys.cinnamon.reportium.events;

import io.magentys.cinnamon.events.ScenarioEvent;

public class BeforeScenarioEvent implements ScenarioEvent {
    private final String scenarioName;
    private final String tags;

    public BeforeScenarioEvent(final String scenarioName, final String tags) {
        this.scenarioName = scenarioName;
        this.tags = tags;
    }

    @Override
    public String getScenarioTags() {
        return this.tags;
    }

    @Override
    public String getScenarioName() {
        return this.scenarioName;
    }
}
