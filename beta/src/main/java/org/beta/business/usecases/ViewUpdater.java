package org.beta.business.usecases;

import org.beta.business.gateways.ViewRepository;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.beta.business.generic.DomainUpdater;
import org.beta.domain.events.CommentAdded;
import org.beta.domain.events.PostCreated;
import org.beta.domain.events.ReactionAdded;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
                    postCreated.aggregateParentId(),
                    postCreated.getAuthor(),
                    postCreated.getTitle()
            );

            viewRepository.saveNewPost(newPost);
        });

        listen((CommentAdded commentAdded) -> {
            CommentViewModel newComment = new CommentViewModel(
                    commentAdded.getId(),
                    // What's the difference between PostID & AggregateRootID?
                    commentAdded.aggregateRootId(),
                    commentAdded.getAuthor(),
                    commentAdded.getContent()
            );

            viewRepository.saveNewComment(newComment);
        });

        listen((ReactionAdded reactionAdded) -> {
            ReactionViewModel newReaction = new ReactionViewModel(
                    reactionAdded.getId(),
                    reactionAdded.aggregateRootId(),
                    reactionAdded.getAuthor(),
                    reactionAdded.getReactionType()
            );

            viewRepository.saveNewReaction(newReaction);
        });

        // TODO: Add listeners for the remaining domainEvents
    }
}
