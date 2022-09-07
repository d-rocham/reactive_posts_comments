package org.alpha.business.usecases;

import org.alpha.business.gateways.EventBus;
import org.alpha.business.gateways.EventRepository;
import org.alpha.business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.alpha.domain.Post;
import org.alpha.domain.commands.EditReaction;
import org.alpha.domain.identifiers.PostID;
import org.alpha.domain.identifiers.ReactionID;
import org.alpha.domain.valueobjects.ReactionType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EditReactionUseCase extends UseCaseForCommand<EditReaction> {

    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public EditReactionUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    public Flux<DomainEvent> apply(Mono<EditReaction> editReactionMono) {
        return editReactionMono
                .flatMapMany(command -> eventRepository.findById(command.getPostID())
                        .collectList()
                        .flatMapIterable(eventList -> {
                                    Post targetPost = Post.from(
                                            PostID.of(command.getPostID()),
                                            eventList
                                    );

                                    targetPost.editReaction(
                                            ReactionID.of(command.getReactionID()),
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
