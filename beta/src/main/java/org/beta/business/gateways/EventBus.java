package org.beta.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;

public interface EventBus {

    void publish(DomainEvent event);
    void publishError(Throwable errorEvent);

    void publishPost(PostViewModel newPostModel);

    void publishComment(CommentViewModel newCommentModel);

    // TODO: create & implement publish method for each usecase
    void publishReaction(DomainEvent reactionAddedGenericEvent);

}
