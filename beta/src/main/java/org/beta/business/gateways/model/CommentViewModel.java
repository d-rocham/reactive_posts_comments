package org.beta.business.gateways.model;

import org.beta.domain.events.CommentAdded;
import org.beta.domain.events.CommentEdited;

public class CommentViewModel extends ViewModel{

    private String id;
    private String postId;
    private String author;
    private String content;

    public CommentViewModel() {

    }

    private CommentViewModel(String id, String postId, String author, String content) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
    }

    private CommentViewModel(String id, String postId, String content) {
        this.id = id;
        this.postId = postId;
        this.content = content;
    }

    public static CommentViewModel fromCreationEvent(CommentAdded commentAddedEvent) {
        return new CommentViewModel(
                commentAddedEvent.getId(),
                commentAddedEvent.aggregateRootId(),
                commentAddedEvent.getAuthor(),
                commentAddedEvent.getContent()
        );
    }

    public static CommentViewModel fromEditionEvent(CommentEdited commentEditedEvent) {
        return new CommentViewModel(
                commentEditedEvent.getId(),
                commentEditedEvent.aggregateRootId(),
                commentEditedEvent.getContent()
        );
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
