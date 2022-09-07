package org.alpha.business.usecases;

import org.alpha.business.gateways.EventBus;
import org.alpha.business.gateways.EventRepository;
import org.alpha.business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.alpha.domain.Post;
import org.alpha.domain.commands.CreatePost;
import org.alpha.domain.identifiers.PostID;
import org.alpha.domain.valueobjects.Author;
import org.alpha.domain.valueobjects.Title;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreatePostUseCase extends UseCaseForCommand<CreatePost> {

    /* Why don't we use dependency injection to use both repositories?
    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventBus eventBus; */

    private final EventRepository eventRepository;

    private final EventBus eventBus;

    public CreatePostUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    // Why does it return a Flux instead of a Mono?
    public Flux<DomainEvent> apply(Mono<CreatePost> createPostMono) {
        return createPostMono
                .flatMapIterable(command -> {
                    // Create the Post AR to grab its event
                    Post newPost = new Post(
                            PostID.of(command.getPostID()),
                            new Author(command.getAuthor()),
                            new Title(command.getTitle())
                    );

                    // Return the event from the newly created Post
                    return newPost.getUncommittedChanges();
                })
                // Extract the event, stored in the DB
                .flatMap(newEvent -> eventRepository
                        .saveEvent(newEvent)
                        .thenReturn(newEvent)
                )
                // Finally, publish it to RabbitMQ and
                // continue with the Post creation itself in the next microservice
                .doOnNext(eventBus::publish);
    }
}
