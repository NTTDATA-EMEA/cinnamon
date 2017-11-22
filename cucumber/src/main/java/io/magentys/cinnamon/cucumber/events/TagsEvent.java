package io.magentys.cinnamon.cucumber.events;

import gherkin.formatter.model.Tag;
import io.magentys.cinnamon.events.TagEvent;

import java.util.Set;

public class TagsEvent implements TagEvent {
    String tags;

    public TagsEvent(String tags) {
        this.tags=tags;
    }

    public String getTags() {
        return this.tags;
    }
}
