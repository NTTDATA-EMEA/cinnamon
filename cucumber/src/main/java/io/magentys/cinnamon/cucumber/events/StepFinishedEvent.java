package io.magentys.cinnamon.cucumber.events;

import cucumber.api.Result;
import cucumber.api.TestCase;
import cucumber.api.event.EmbedEvent;
import cucumber.runner.EventBus;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.events.TestStepFinishedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StepFinishedEvent implements TestStepFinishedEvent {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final EventBus bus;
    private final TestCase testCase;
    private final Result result;

    public StepFinishedEvent(final EventBus bus, final TestCase testCase, final Result result) {
        this.bus = bus;
        this.testCase = testCase;
        this.result = result;
    }

    @Override
    public boolean isFailed() {
        return "failed".equals(getStatus());
    }

    @Override
    public String getStatus() {
        return result.getStatus().lowerCaseName();
    }

    @Override
    public void attach(Attachment attachment) {
        if (bus != null) {
            logger.debug("Firing embed event for scenario: " + testCase.getName());
            bus.send(new EmbedEvent(bus.getTime(), testCase, attachment.getBytes(), attachment.getMimeType()));
        }
    }
}
