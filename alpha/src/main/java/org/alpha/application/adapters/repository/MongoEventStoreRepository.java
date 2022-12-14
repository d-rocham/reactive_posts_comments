package org.alpha.application.adapters.repository;

import org.alpha.application.generic.models.StoredEvent;
import org.alpha.business.gateways.EventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Repository
public class MongoEventStoreRepository implements EventRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final Gson gson = new Gson();

    public MongoEventStoreRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        var findByIdQuery = new Query(Criteria
                .where("aggregateRootId")
                .is(aggregateId));

        return reactiveMongoTemplate
                .find(findByIdQuery, DocumentEventStored.class)
                .sort(Comparator.comparing(documentEventStored ->
                        documentEventStored.getStoredEvent().getOcurredOn()))
                .map(documentEventStored -> {
                    try {
                        return (DomainEvent) gson.fromJson(
                                documentEventStored.getStoredEvent().getEventBody(),
                                Class.forName(documentEventStored.getStoredEvent().getTypeName()));
                    } catch (ClassNotFoundException exception) {
                        exception.printStackTrace();
                        throw new IllegalArgumentException("Requested domain event was not found");
                    }
                });
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        DocumentEventStored documentEventStored = new DocumentEventStored(
                event.aggregateRootId(),
                new StoredEvent(
                        gson.toJson(event),
                        new Date(),
                        event.getClass().getCanonicalName()
                ));

        return reactiveMongoTemplate.save(documentEventStored)
                .map(eventStored -> {
                    try {
                        return (DomainEvent) gson.fromJson(
                                eventStored.getStoredEvent().getEventBody(),
                                Class.forName(eventStored.getStoredEvent().getTypeName()));
                    } catch (ClassNotFoundException exception) {
                        exception.printStackTrace();
                        throw new IllegalArgumentException("Requested domain event was not found");
                    }
                });
    }
}
