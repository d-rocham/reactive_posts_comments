package org.beta.business.gateways.model;

public class ReactionViewModel {

    private String id;
    private String postId;
    private String author;
    private String reactionType;

    public ReactionViewModel() {

    }

    public ReactionViewModel(String id, String postId, String author, String reactionType) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.reactionType = reactionType;
    }

    public ReactionViewModel(String id, String postId, String reactionType) {
        this.id = id;
        this.postId = postId;
        this.reactionType = reactionType;
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
