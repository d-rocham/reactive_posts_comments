package org.beta.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class ReactionEdited extends DomainEvent {
    private String id;
    private String reactionType;

    public ReactionEdited(String id, String reactionType) {
        super("domain.reactionedited");
        this.id = id;
        this.reactionType = reactionType;
    }

    public String getId() {
        return id;
    }

    public String getReactionType() {
        return reactionType;
    }
}
