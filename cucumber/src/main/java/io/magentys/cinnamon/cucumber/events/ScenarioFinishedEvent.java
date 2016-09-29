package io.magentys.cinnamon.cucumber.events;

import static java.util.Arrays.asList;
import gherkin.formatter.model.Result;
import io.magentys.cinnamon.events.TestCaseFinishedEvent;

import java.util.List;

public class ScenarioFinishedEvent implements TestCaseFinishedEvent {
    private static final List<String> SEVERITY = asList("passed", "skipped", "pending", "undefined", "failed");
    private final List<Result> results;

    public ScenarioFinishedEvent(final List<Result> results) {
        this.results = results;
    }

    @Override
    public boolean isFailed() {
        return "failed".equals(getStatus());
    }

    @Override
    public String getStatus() {
        int pos = 0;
        for (Result result : results) {
            pos = Math.max(pos, SEVERITY.indexOf(result.getStatus()));
        }
        return SEVERITY.get(pos);
    }
}
