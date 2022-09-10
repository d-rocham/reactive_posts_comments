package org.gamma.application.bus.model;

import java.util.ArrayList;
import java.util.List;

public class PostModel {

    private String aggregateId;
    private String author;
    private String title;

    private List<CommentModel> comments;
    private List<ReactionModel> reactions;

    public PostModel() {
        this.comments = new ArrayList<>();
        this.reactions = new ArrayList<>();
    }

    public PostModel(String aggregateId, String author, String title, List<CommentModel> comments, List<ReactionModel> reactions) {
        this.aggregateId = aggregateId;
        this.author = author;
        this.title = title;
        this.comments = comments;
        this.reactions = reactions;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public List<ReactionModel> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionModel> reactions) {
        this.reactions = reactions;
    }
}
