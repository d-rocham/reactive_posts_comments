package org.beta.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus {

    void publish(DomainEvent event);
    void publishError(Throwable errorEvent);

    void publishPost(DomainEvent postCreatedGenericEvent);

    void publishComment(DomainEvent commentAddedGenericEvent);

    // TODO: create & implement publish method for each usecase
    void publishReaction(DomainEvent reactionAddedGenericEvent);

}
