package org.alpha.domain.commands;

import co.com.sofka.domain.generic.Command;

public class AddReaction extends Command {
    private String postID;
    private String reactionID;
    private String author;
    private String reactionType;

    public AddReaction() {

    }

    public AddReaction(String postID, String reactionID, String author, String reactionType) {
        this.postID = postID;
        this.reactionID = reactionID;
        this.author = author;
        this.reactionType = reactionType;
    }

    public String getPostID() {
        return postID;
    }

    public String getReactionID() {
        return reactionID;
    }

    public String getAuthor() {
        return author;
    }

    public String getReactionType() {
        return reactionType;
    }
}
