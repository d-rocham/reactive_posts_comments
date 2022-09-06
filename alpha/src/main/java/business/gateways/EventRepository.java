package business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {

    Flux<DomainEvent> findPostById (String id);
    Mono<DomainEvent> saveEvent(DomainEvent event);
}
