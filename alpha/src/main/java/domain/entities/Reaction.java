package domain.entities;

import co.com.sofka.domain.generic.Entity;
import domain.identifiers.ReactionID;
import domain.valueobjects.Author;
import domain.valueobjects.ReactionType;

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
}
