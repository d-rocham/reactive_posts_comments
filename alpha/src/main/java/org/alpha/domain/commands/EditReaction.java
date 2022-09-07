package org.alpha.domain.commands;

import co.com.sofka.domain.generic.Command;

public class EditReaction extends Command {

    private String postID;
    private String reactionID;
    private String reactionType;

    public EditReaction() {
    }

    public EditReaction(String postID, String reactionID, String reactionType) {
        this.postID = postID;
        this.reactionID = reactionID;
        this.reactionType = reactionType;
    }

    public String getPostID() {
        return postID;
    }

    public String getReactionID() {
        return reactionID;
    }

    public String getReactionType() {
        return reactionType;
    }
}
