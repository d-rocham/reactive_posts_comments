package org.alpha.domain.commands;

import co.com.sofka.domain.generic.Command;

public class EditPost extends Command {
    private String postID;
    private String title;

    public EditPost() {
    }

    public EditPost(String postID, String title) {
        this.postID = postID;
        this.title = title;
    }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }
}
