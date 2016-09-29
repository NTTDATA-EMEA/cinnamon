package io.magentys.cinnamon.cucumber.events;

import gherkin.formatter.Reporter;
import gherkin.formatter.model.Result;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.events.TestStepFinishedEvent;

public class StepFinishedEvent implements TestStepFinishedEvent {
    private final Result result;
    private final Reporter reporter;

    public StepFinishedEvent(final Result result, final Reporter reporter) {
        this.result = result;
        this.reporter = reporter;
    }

    @Override
    public boolean isFailed() {
        return "failed".equals(getStatus());
    }

    @Override
    public String getStatus() {
        return result.getStatus();
    }

    @Override
    public void attach(Attachment attachment) {
        reporter.embedding(attachment.getMimeType(), attachment.getBytes());
    }
}
