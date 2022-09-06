package business.usecases;

import business.gateways.EventBus;
import business.gateways.EventRepository;
import business.generic.UseCaseForCommand;
import co.com.sofka.domain.generic.DomainEvent;
import domain.Post;
import domain.commands.EditComment;
import domain.identifiers.CommentID;
import domain.identifiers.PostID;
import domain.valueobjects.Content;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EditCommentUseCase extends UseCaseForCommand<EditComment> {

    private final EventRepository eventRepository;
    private final EventBus eventBus;

    public EditCommentUseCase(EventRepository eventRepository, EventBus eventBus) {
        this.eventRepository = eventRepository;
        this.eventBus = eventBus;
    }

    public Flux<DomainEvent> apply(Mono<EditComment> editCommentMono) {
        return editCommentMono
                .flatMapMany(command -> eventRepository.findById(command.getPostID())
                        .collectList()
                        .flatMapIterable(eventList -> {
                            Post targetPost = Post.from(
                                    PostID.of(command.getPostID()),
                                    eventList
                            );

                            targetPost.editComment(
                                    CommentID.of(command.getCommentID()),
                                    new Content(command.getContent())
                            );

                            return targetPost.getUncommittedChanges();
                        }))
                .flatMap(newEvent -> eventRepository
                        .saveEvent(newEvent)
                        .thenReturn(newEvent))
                .doOnNext(eventBus::publish);
    }
}
