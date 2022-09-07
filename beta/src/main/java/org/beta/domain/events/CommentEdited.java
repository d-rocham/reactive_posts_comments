package org.beta.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class CommentEdited extends DomainEvent {
    private String id;
    private String content;

    public CommentEdited(String id, String content) {
        super("domain.commentedited");
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
