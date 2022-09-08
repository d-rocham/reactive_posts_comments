package org.beta.domain.entities;

import co.com.sofka.domain.generic.Entity;
import org.beta.domain.identifiers.ReactionID;
import org.beta.domain.valueobjects.Author;
import org.beta.domain.valueobjects.ReactionType;

import java.util.Objects;

public class Reaction extends Entity<ReactionID> {

    private Author author;
    private ReactionType reactionType;

    public Reaction(ReactionID entityId, Author author, ReactionType reactionType) {
        super(entityId);
        this.author = author;
        this.reactionType = reactionType;
    }

    public Author author() {
        return author;
    }

    public ReactionType reactionType() {
        return reactionType;
    }

    // Behaviours

    public void modifyReactionType(ReactionType updatedReactionType) {
        this.reactionType = Objects.requireNonNull(updatedReactionType);
    }
}
