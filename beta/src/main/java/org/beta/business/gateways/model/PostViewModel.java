package org.beta.business.gateways.model;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel {

    // private String id;

    // Why does it need the aggregateId?
    private String aggregateId;

    private String author;

    private String title;

    private List<CommentViewModel> comments;

    private List<ReactionViewModel> reactions;

    // TODO: What's the explanation for this constructor? Where is it used?
    public PostViewModel() {
        this.comments = new ArrayList<>();
        this.reactions = new ArrayList<>();
    }

    public PostViewModel(/*String id,*/ String aggregateId, String author, String title) {
        // this.id = id;
        this.aggregateId = aggregateId;
        this.author = author;
        this.title = title;
        this.comments = new ArrayList<>();
        this.reactions = new ArrayList<>();
    }

    /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    } */

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

    public List<CommentViewModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentViewModel> comments) {
        this.comments = comments;
    }

    public List<ReactionViewModel> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionViewModel> reactions) {
        this.reactions = reactions;
    }
}
