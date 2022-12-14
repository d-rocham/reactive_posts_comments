package org.gamma.domain;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.gamma.domain.entities.Comment;
import org.gamma.domain.entities.Reaction;
import org.gamma.domain.events.*;
import org.gamma.domain.identifiers.CommentID;
import org.gamma.domain.identifiers.PostID;
import org.gamma.domain.identifiers.ReactionID;
import org.gamma.domain.valueobjects.Author;
import org.gamma.domain.valueobjects.Content;
import org.gamma.domain.valueobjects.ReactionType;
import org.gamma.domain.valueobjects.Title;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Post extends AggregateEvent<PostID> {

    protected Author author;
    protected Title title;

    protected List<Comment> commentList;
    protected List<Reaction> reactionList;

    public Post(PostID entityId, Author author, Title title) {
        super(entityId);
        subscribe(new PostChange(this));
        appendChange(new PostCreated(
                title.value(),
                author.value()
        )).apply();
    }

    private Post(PostID postID) {
        super(postID);
        subscribe(new PostChange(this));
    }

    public static Post from(PostID id, List<DomainEvent> postEvents) {
        Post post = new Post(id);
        postEvents.forEach(post::applyEvent);

        return post;
    }

    // Finders

    public Optional<Comment> getCommentById(CommentID targetCommentId) {
        return commentList.stream()
                .filter(comment -> comment.identity().equals(targetCommentId))
                .findFirst();
    }

    public Optional<Reaction> getReactionById(ReactionID targetReactionId) {
        return reactionList.stream()
                .filter(reaction -> reaction.identity().equals(targetReactionId))
                .findFirst();
    }


    // Behaviours

    public void addComment(CommentID commentID, Author author, Content content) {
        Objects.requireNonNull(commentID);
        Objects.requireNonNull(author);
        Objects.requireNonNull(content);

        // Create event
        appendChange(
                new CommentAdded(commentID.value(), author.value(), content.value())
        ).apply();

    }

    public void addReaction(ReactionID reactionID, Author author, ReactionType reactionType) {
        Objects.requireNonNull(reactionID);
        Objects.requireNonNull(author);
        Objects.requireNonNull(reactionType);

        // Create event
        appendChange(
                new ReactionAdded(reactionID.value(), author.value(), reactionType.value())
        ).apply();
    }

    public void editComment(CommentID commentID, Content content) {
        Objects.requireNonNull(commentID);
        Objects.requireNonNull(content);

        // Create event
        appendChange(
                new CommentEdited(commentID.value(), content.value())
        ).apply();
    }

    public void editReaction(ReactionID reactionID, ReactionType reactionType) {
        Objects.requireNonNull(reactionID);
        Objects.requireNonNull(reactionType);

        appendChange(
                new ReactionEdited(reactionID.value(), reactionType.value())
        ).apply();
    }

    public void editPost(PostID postID, Title title) {
        Objects.requireNonNull(postID);
        Objects.requireNonNull(title);

        appendChange(
                new PostEdited(postID.value(), title.value())
        ).apply();
    }

    public void modifyTitle(Title updatedTitle) {
        this.title = Objects.requireNonNull(updatedTitle);
    }

}
