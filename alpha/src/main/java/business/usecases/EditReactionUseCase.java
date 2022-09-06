package business.usecases;

import business.gateways.EventBus;
import business.gateways.EventRepository;
import business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import domain.Post;
import domain.commands.EditReaction;
import domain.identifiers.PostID;
import domain.identifiers.ReactionID;
import domain.valueobjects.ReactionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
