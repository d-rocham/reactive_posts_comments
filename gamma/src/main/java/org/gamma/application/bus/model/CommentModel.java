package org.gamma.application.bus.model;

public class CommentModel {

    private String id;
    private String postId;
    private String author;
    private String content;

    public CommentModel(String id, String postId, String author, String content) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
