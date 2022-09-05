package domain.commands;

import co.com.sofka.domain.generic.Command;

public class EditPost extends Command {
    private String postID;
    private String tittle;

    public EditPost() {
    }

    public EditPost(String postID, String tittle) {
        this.postID = postID;
        this.tittle = tittle;
    }

    public String getPostID() {
        return postID;
    }

    public String getTittle() {
        return tittle;
    }
}
