package org.beta.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus {

    // TODO: create publish method for eeach usecase

    void publish(DomainEvent event);
    void publishError(Throwable errorEvent);

}
