package org.beta.domain;

import co.com.sofka.domain.generic.EventChange;
import org.beta.domain.entities.Comment;
import org.beta.domain.entities.Reaction;
import org.beta.domain.events.*;
import org.beta.domain.identifiers.CommentID;
import org.beta.domain.identifiers.ReactionID;
import org.beta.domain.valueobjects.Author;
import org.beta.domain.valueobjects.Content;
import org.beta.domain.valueobjects.ReactionType;
import org.beta.domain.valueobjects.Title;
import org.beta.domain.events.PostCreated;

import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;


public class PostChange extends EventChange {

    public PostChange(Post post) {
        apply((PostCreated postCreated) -> {
            post.author = new Author(postCreated.getAuthor());
            post.title = new Title(postCreated.getTitle());

            post.commentList = new ArrayList<>();
            post.reactionList = new ArrayList<>();
        });

        apply((CommentAdded commentAdded) -> {
            Comment newComment = new Comment(
                    CommentID.of(commentAdded.getId()),
                    new Author(commentAdded.getAuthor()),
                    new Content(commentAdded.getContent())
            );

            // Why do we access the property instead of using the Post.addComment() behaviour method?
            // RE: Post.addComment() doesn't add comments to the list, it adds events.
            post.commentList.add(newComment);
        });

        apply((ReactionAdded reactionAdded) -> {
            Reaction newReaction = new Reaction(
                    ReactionID.of(reactionAdded.getId()),
                    new Author(reactionAdded.getAuthor()),
                    new ReactionType(reactionAdded.getReactionType())
            );

            post.reactionList.add(newReaction);
        });

        apply((CommentEdited commentEdited) -> {
            // Why does triggering the exception returns a comment, not an optional?
            var targetComment = post
                    .getCommentById(CommentID.of(commentEdited.getId()))
                    .orElseThrow(() -> new InaccessibleObjectException("No comment exists for the requested id"));

            targetComment.modifyContent(new Content(commentEdited.getContent()));
        });

        apply((ReactionEdited reactionEdited) -> {
            var targetReaction = post
                    .getReactionById(ReactionID.of(reactionEdited.getId()))
                    .orElseThrow(() -> new InaccessibleObjectException("No comment exists for the requested id"));

            targetReaction.modifyReactionType(new ReactionType(reactionEdited.getReactionType()));
        });

        apply((PostEdited postEdited) -> {
            post.modifyTitle(new Title(postEdited.getTitle()));
        });

    }
}
