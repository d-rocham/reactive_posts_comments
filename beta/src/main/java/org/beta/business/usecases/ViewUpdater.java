package org.beta.business.usecases;

import org.beta.business.gateways.ViewRepository;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.beta.business.generic.DomainUpdater;
import org.beta.domain.events.*;
import org.springframework.stereotype.Service;


@Service
public class ViewUpdater extends DomainUpdater {
    private final ViewRepository viewRepository;

    public ViewUpdater(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;

        // Listeners for different domain events.

        // Each event first creates a ViewModel for the event
        // Then stores the ViewModel in the repository

        listen((PostCreated postCreated) -> {
            PostViewModel newPost = new PostViewModel(
                    postCreated.aggregateRootId(),
                    postCreated.getAuthor(),
                    postCreated.getTitle()
            );

            viewRepository.saveNewPost(newPost).subscribe();
        });

        listen((CommentAdded commentAdded) -> {
            CommentViewModel newComment = new CommentViewModel(
                    commentAdded.getId(),
                    // What's the difference between PostID & AggregateRootID?
                    commentAdded.aggregateRootId(),
                    commentAdded.getAuthor(),
                    commentAdded.getContent()
            );

            viewRepository.saveNewComment(newComment).subscribe();
        });

        listen((ReactionAdded reactionAdded) -> {
            ReactionViewModel newReaction = new ReactionViewModel(
                    reactionAdded.getId(),
                    reactionAdded.aggregateRootId(),
                    reactionAdded.getAuthor(),
                    reactionAdded.getReactionType()
            );

            viewRepository.saveNewReaction(newReaction).subscribe();
        });

        listen((PostEdited postEdited) -> {
            PostViewModel editedPostModel = new PostViewModel(
                    postEdited.aggregateRootId(),
                    postEdited.getTitle()
            );

            viewRepository.editPost(editedPostModel);
        });

        listen((CommentEdited commentEdited) -> {
            CommentViewModel editedCommentModel = new CommentViewModel(
                    commentEdited.getId(),
                    commentEdited.aggregateRootId(),
                    commentEdited.getContent()
            );

            viewRepository.editComment(editedCommentModel);
        });

        listen((ReactionEdited reactionEdited) -> {
            ReactionViewModel editedReactionModel = new ReactionViewModel(
                    reactionEdited.getId(),
                    reactionEdited.aggregateRootId(),
                    reactionEdited.getReactionType()
            );

            viewRepository.editReaction(editedReactionModel);
        });

    }
}
