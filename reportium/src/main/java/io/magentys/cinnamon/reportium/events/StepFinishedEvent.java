package io.magentys.cinnamon.reportium.events;

import gherkin.formatter.Reporter;
import gherkin.formatter.model.Result;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.events.TestStepFinishedEvent;

public class StepFinishedEvent implements TestStepFinishedEvent {
    private final Result result;
    private final Reporter reporter;
    private final String errorMessage;
    private final Throwable error;

    public StepFinishedEvent(final Result result, final Reporter reporter, final String errorMessage, final Throwable error) {
        this.result = result;
        this.reporter = reporter;
        this.error = error;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Throwable getError() {
        return this.error;
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
