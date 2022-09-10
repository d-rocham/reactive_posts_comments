package org.beta.business.gateways.model;

import org.beta.domain.events.PostCreated;
import org.beta.domain.events.PostEdited;

import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel{

    private String aggregateId;

    private String author;

    private String title;

    private List<CommentViewModel> comments;

    private List<ReactionViewModel> reactions;

    public PostViewModel() {
        this.comments = new ArrayList<>();
        this.reactions = new ArrayList<>();
    }

    private PostViewModel(String aggregateId, String author, String title) {
        this.aggregateId = aggregateId;
        this.author = author;
        this.title = title;
        this.comments = new ArrayList<>();
        this.reactions = new ArrayList<>();
    }

    private PostViewModel(String aggregateId, String title) {
        this.aggregateId = aggregateId;
        this.title = title;
    }

    public static PostViewModel fromCreationEvent(PostCreated postCreatedEvent) {
        return new PostViewModel(
                postCreatedEvent.aggregateRootId(),
                postCreatedEvent.getAuthor(),
                postCreatedEvent.getTitle()
        );
    }

    public static PostViewModel fromEditionEvent(PostEdited postEditedEvent) {
        return new PostViewModel(
                postEditedEvent.aggregateRootId(),
                postEditedEvent.getTitle()
        );
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
