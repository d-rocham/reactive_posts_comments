package org.beta.business.gateways.model;

import org.beta.domain.events.ReactionAdded;
import org.beta.domain.events.ReactionEdited;

public class ReactionViewModel extends ViewModel{

    private String id;
    private String postId;
    private String author;
    private String reactionType;

    public ReactionViewModel() {

    }

    private ReactionViewModel(String id, String postId, String author, String reactionType) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.reactionType = reactionType;
    }

    private ReactionViewModel(String id, String postId, String reactionType) {
        this.id = id;
        this.postId = postId;
        this.reactionType = reactionType;
    }

    public static ReactionViewModel fromCreationEvent(ReactionAdded reactionAddedEvent) {
        return new ReactionViewModel(
                reactionAddedEvent.getId(),
                reactionAddedEvent.aggregateRootId(),
                reactionAddedEvent.getAuthor(),
                reactionAddedEvent.getReactionType()
        );
    }

    public static ReactionViewModel fromEditionEvent(ReactionEdited reactionEditedEvent) {
        return new ReactionViewModel(
                reactionEditedEvent.getId(),
                reactionEditedEvent.aggregateRootId(),
                reactionEditedEvent.getReactionType()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }
}
