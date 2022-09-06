package business.usecases;

import business.gateways.EventBus;
import business.gateways.EventRepository;
import business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import domain.Post;
import domain.commands.EditPost;
import domain.identifiers.PostID;
import domain.valueobjects.Title;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EditPostUseCase extends UseCaseForCommand<EditPost> {
    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public EditPostUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    public Flux<DomainEvent> apply(Mono<EditPost> editPostMono) {
        return editPostMono
                .flatMapMany(command -> eventRepository.findById(command.getPostID())
                        .collectList()
                        .flatMapIterable(eventList -> {
                            Post targetPost = Post.from(
                                    PostID.of(command.getPostID()),
                                    eventList
                            );

                            targetPost.editPost(
                                    PostID.of(command.getPostID()),
                                    new Title(command.getTitle())
                            );

                            return targetPost.getUncommittedChanges();
                        }))
                .flatMap(newEvent -> eventRepository
                        .saveEvent(newEvent)
                        .thenReturn(newEvent))
                .doOnNext(eventBus::publish);
    }
}
