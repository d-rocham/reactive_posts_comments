package org.alpha.domain.commands;

import co.com.sofka.domain.generic.Command;
import lombok.Setter;
import lombok.ToString;

// TODO: Check if annotations below are actually necessary
@Setter
@ToString
public class CreatePost extends Command {

    private String postID;
    private String author;
    private String title;

    // What is the purpose of this empty constructor
    public CreatePost() {

    }

    public CreatePost(String postID, String author, String title) {
        this.postID = postID;
        this.author = author;
        this.title = title;
    }

    public String getPostID() {
        return postID;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
