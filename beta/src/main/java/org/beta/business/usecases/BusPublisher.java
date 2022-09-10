package org.beta.business.usecases;

import org.beta.business.gateways.EventBus;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.beta.business.generic.DomainUpdater;
import org.beta.domain.events.*;
import org.springframework.stereotype.Service;

@Service
public class BusPublisher extends DomainUpdater {

    private final EventBus eventBus;

    public BusPublisher(EventBus eventBus) {
        this.eventBus = eventBus;

        listen((PostCreated postCreated) -> eventBus
                .publishPost(PostViewModel.fromCreationEvent(postCreated)));

        listen((CommentAdded commentAdded) -> eventBus
                .publishComment(CommentViewModel.fromCreationEvent(commentAdded)));

        listen((ReactionAdded reactionAdded) -> eventBus
                .publishReaction(ReactionViewModel.fromCreationEvent(reactionAdded)));

        listen((PostEdited postEdited) -> eventBus
                .publishPostEdition(PostViewModel.fromEditionEvent(postEdited)));

        listen((CommentEdited commentEdited) -> eventBus
                .publishCommentEdition(CommentViewModel.fromEditionEvent(commentEdited)));

        listen((ReactionEdited reactionEdited) -> eventBus
                .publishReactionEdition(ReactionViewModel.fromEditionEvent(reactionEdited)));
    }
}
