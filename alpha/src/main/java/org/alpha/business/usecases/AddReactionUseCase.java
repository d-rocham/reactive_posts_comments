package org.alpha.business.usecases;

import org.alpha.business.gateways.EventBus;
import org.alpha.business.gateways.EventRepository;
import org.alpha.business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.alpha.domain.Post;
import org.alpha.domain.commands.AddReaction;
import org.alpha.domain.identifiers.PostID;
import org.alpha.domain.identifiers.ReactionID;
import org.alpha.domain.valueobjects.Author;
import org.alpha.domain.valueobjects.ReactionType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AddReactionUseCase extends UseCaseForCommand<AddReaction> {
    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public AddReactionUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AddReaction> addReactionMono) {
        return addReactionMono
                .flatMapMany(command -> eventRepository.findById(command.getPostID())
                        .collectList()
                        .flatMapIterable(eventList -> {
                                Post targetPost = Post.from(
                                        PostID.of(command.getPostID()),
                                        eventList);

                                targetPost.addReaction(
                                        ReactionID.of(command.getReactionID()),
                                        new Author(command.getAuthor()),
                                        new ReactionType(command.getReactionType())
                                );

                                return targetPost.getUncommittedChanges();
                        }))
                .flatMap(newEvent -> eventRepository
                        .saveEvent(newEvent)
                        .thenReturn(newEvent))
                .doOnNext(eventBus::publish);
    }
}
