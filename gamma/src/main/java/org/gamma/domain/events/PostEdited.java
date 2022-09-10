package org.gamma.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class PostEdited extends DomainEvent {
    private String id;
    private String title;

    public PostEdited(String id, String title) {
        super("domain.postEdited");
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
