package org.beta.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class ReactionAdded extends DomainEvent {
    private String id;
    private String author;
    private String reactionType;

    public ReactionAdded(String id, String author, String reactionType) {
        super("domain.reactionadded");
        this.id = id;
        this.author = author;
        this.reactionType = reactionType;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getReactionType() {
        return reactionType;
    }
}
