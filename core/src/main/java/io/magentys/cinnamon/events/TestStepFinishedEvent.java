package io.magentys.cinnamon.events;

public interface TestStepFinishedEvent extends StatusEvent, AttachmentEvent {
    String getErrorMessage();

    Throwable getError();
}
