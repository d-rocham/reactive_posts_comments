package business.usecases;

import business.gateways.EventBus;
import business.gateways.EventRepository;
import business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import domain.Post;
import domain.commands.AddComment;
import domain.identifiers.CommentID;
import domain.identifiers.PostID;
import domain.valueobjects.Author;
import domain.valueobjects.Content;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AddCommentUseCase extends UseCaseForCommand<AddComment> {

    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public AddCommentUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

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
                )
                // Save event in DB, publish to RabbitMQ
                .flatMap(newEvent -> eventRepository
                        .saveEvent(newEvent)
                        .thenReturn(newEvent))
                .doOnNext(eventBus::publish);
    }
}