package org.alpha.business.usecases;

import org.alpha.business.gateways.EventBus;
import org.alpha.business.gateways.EventRepository;
import org.alpha.business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.alpha.domain.Post;
import org.alpha.domain.commands.AddComment;
import org.alpha.domain.identifiers.CommentID;
import org.alpha.domain.identifiers.PostID;
import org.alpha.domain.valueobjects.Author;
import org.alpha.domain.valueobjects.Content;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AddCommentUseCase extends UseCaseForCommand<AddComment> {

    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public AddCommentUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AddComment> addCommentMono) {
        return addCommentMono
                // Find parent post & rebuild its event list.
                .flatMapMany(command -> eventRepository.findById(command.getPostID())
                        .collectList()
                        .flatMapIterable(eventList -> {
                            Post targetPost = Post.from(
                                    PostID.of(command.getPostID()),
                                    eventList
                            );
                            // Add comment to Post
                            targetPost.addComment(
                                    CommentID.of(command.getCommentID()),
                                    new Author(command.getAuthor()),
                                    new Content(command.getContent())
                            );
                            // Retrieve CommentAdded event from previous operation
                            return targetPost.getUncommittedChanges();
                        })
                        // Save event in DB, publish to RabbitMQ
                        .map(newEvent -> {
                            eventBus.publish(newEvent);
                            return newEvent;
                        })
                        .flatMap(eventRepository::saveEvent)
                );
    }
}