package org.alpha.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {

    // Each useCase calls the interface EventRepository instead of its implementation, MongoEventStoreRepository
    // yet the application automatically knows to call the implemented method. How?
    Flux<DomainEvent> findById (String id);
    Mono<DomainEvent> saveEvent(DomainEvent event);
}
