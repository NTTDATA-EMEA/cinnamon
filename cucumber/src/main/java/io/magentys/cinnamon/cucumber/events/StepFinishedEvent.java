package io.magentys.cinnamon.cucumber.events;

import cucumber.api.event.EmbedEvent;
import cucumber.api.event.Event;
import cucumber.api.event.EventListener;
import cucumber.api.Result;
import io.magentys.cinnamon.events.Attachment;
import io.magentys.cinnamon.events.TestStepFinishedEvent;

import java.util.Date;

public class StepFinishedEvent implements TestStepFinishedEvent {
    private final Result result;
    private final EventListener eventListener;

    public StepFinishedEvent(final Result result, final EventListener eventListener) {
        this.result = result;
        this.eventListener = eventListener;
    }

    @Override
    public boolean isFailed() {
        return "failed".equals(getStatus());
    }

    @Override
    public Result.Type getStatus() {
        return result.getStatus();
    }

    @Override
    public void attach(Attachment attachment) {
        //TODO should i be creating my own timestamp here, or does it need to be passed in somehow/somewhere
        Long timestamp = new Date().getTime();
        //TODO provide TestCase here...
        Event event =  new EmbedEvent(timestamp, null, attachment.getBytes(), attachment.getMimeType());
    }
}
