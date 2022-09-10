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

        /* POST INTERACTIONS */

        listen((PostCreated postCreated) -> viewRepository
                .saveNewPost(PostViewModel.fromCreationEvent(postCreated))
                .subscribe());

        listen((PostEdited postEdited) -> viewRepository
                .editPost(PostViewModel.fromEditionEvent(postEdited))
                .subscribe());

        /* COMMENT INTERACTIONS */

        listen((CommentAdded commentAdded) -> viewRepository
                .saveNewComment(CommentViewModel.fromCreationEvent(commentAdded))
                .subscribe());

        listen((CommentEdited commentEdited) -> viewRepository
                .editComment(CommentViewModel.fromEditionEvent(commentEdited))
                .subscribe());

        /* REACTION INTERACTIONS */

        listen((ReactionAdded reactionAdded) -> viewRepository
                .saveNewReaction(ReactionViewModel.fromCreationEvent(reactionAdded))
                .subscribe());


        listen((ReactionEdited reactionEdited) -> viewRepository
                .editReaction(ReactionViewModel.fromEditionEvent(reactionEdited))
                .subscribe());

    }
}
