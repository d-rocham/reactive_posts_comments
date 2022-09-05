package domain.commands;

import co.com.sofka.domain.generic.Command;

public class EditComment extends Command {
    private String postID;
    private String commentID;
    private String content;

    public EditComment() {
    }

    public EditComment(String postID, String commentID, String content) {
        this.postID = postID;
        this.commentID = commentID;
        this.content = content;
    }

    public String getPostID() {
        return postID;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getContent() {
        return content;
    }
}
