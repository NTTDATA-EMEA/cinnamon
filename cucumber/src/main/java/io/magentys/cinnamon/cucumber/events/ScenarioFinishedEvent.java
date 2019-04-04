package io.magentys.cinnamon.cucumber.events;

import cucumber.api.Result;
import io.magentys.cinnamon.events.TestCaseFinishedEvent;

import java.util.List;

import static java.util.Arrays.asList;

public class ScenarioFinishedEvent implements TestCaseFinishedEvent {
    private static final List<Result.Type> SEVERITY = asList(Result.Type.PASSED, Result.Type.SKIPPED, Result.Type.PENDING, Result.Type.UNDEFINED,
            Result.Type.FAILED);
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
        return SEVERITY.get(pos).lowerCaseName();
    }
}
