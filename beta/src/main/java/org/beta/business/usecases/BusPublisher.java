package org.beta.business.usecases;

import org.beta.business.gateways.EventBus;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.generic.DomainUpdater;
import org.beta.domain.events.CommentAdded;
import org.beta.domain.events.PostCreated;
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
    }
}
