package io.magentys.cinnamon.cucumber.events;

import io.magentys.cinnamon.events.FeatureEvent;

public class BeforeFeatureScenario implements FeatureEvent {

    private final String featureName;

    public BeforeFeatureScenario(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public String getFeatureName() {
        return featureName;
    }
}
