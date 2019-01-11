package io.magentys.cinnamon.cucumber.events;

import static java.util.Arrays.asList;
import cucumber.api.Result;
import io.magentys.cinnamon.events.TestCaseFinishedEvent;

import java.util.List;

public class ScenarioFinishedEvent implements TestCaseFinishedEvent {
    private static final List<Result.Type> SEVERITY = asList(Result.Type.PASSED, Result.Type.SKIPPED, Result.Type.PENDING, Result.Type.UNDEFINED, Result.Type.FAILED);
    private final List<Result> results;

    public ScenarioFinishedEvent(final List<Result> results) {
        this.results = results;
    }

    @Override
    public boolean isFailed() {
        return "failed".equals(getStatus());
    }

    @Override
    public Result.Type getStatus() {
        int pos = 0;
        for (Result result : results) {
            pos = Math.max(pos, SEVERITY.indexOf(result.getStatus()));
        }
        return SEVERITY.get(pos);
    }
}
