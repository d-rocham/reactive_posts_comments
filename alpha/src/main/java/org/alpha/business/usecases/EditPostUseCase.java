package org.alpha.business.usecases;

import org.alpha.business.gateways.EventBus;
import org.alpha.business.gateways.EventRepository;
import org.alpha.business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.alpha.domain.Post;
import org.alpha.domain.commands.EditPost;
import org.alpha.domain.identifiers.PostID;
import org.alpha.domain.valueobjects.Title;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
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
