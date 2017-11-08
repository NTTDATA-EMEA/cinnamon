package io.magentys.cinnamon.cucumber.events;

public class RunScenarioEvent {

    private String scenarioName;

    public RunScenarioEvent(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getScenarioName() {
        return scenarioName;
    }
}
