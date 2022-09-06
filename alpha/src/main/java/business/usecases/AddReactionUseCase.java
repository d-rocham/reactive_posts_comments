package business.usecases;

import business.gateways.EventBus;
import business.gateways.EventRepository;
import business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import domain.Post;
import domain.commands.AddReaction;
import domain.identifiers.PostID;
import domain.identifiers.ReactionID;
import domain.valueobjects.Author;
import domain.valueobjects.ReactionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AddReactionUseCase extends UseCaseForCommand<AddReaction> {
    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public AddReactionUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

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
