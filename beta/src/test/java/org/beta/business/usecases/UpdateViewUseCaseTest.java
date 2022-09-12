package org.beta.business.usecases;

import org.beta.application.adapters.repository.MongoViewRepository;
import org.beta.business.gateways.EventBus;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.beta.domain.events.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class UpdateViewUseCaseTest {

    @Mock
    MongoViewRepository mongoViewRepository;

    @Mock
    EventBus eventBus;

    @Mock
    BusPublisher busPublisher;

    @Mock
    ViewUpdater viewUpdater;

    @Mock
    UpdateViewUseCase updateViewUseCase;

    @BeforeEach
    void init() {
        busPublisher = new BusPublisher(eventBus);
        viewUpdater = new ViewUpdater(mongoViewRepository);

        updateViewUseCase = new UpdateViewUseCase(viewUpdater, busPublisher);
    }

    @Test
    void testPostCreated() {
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        PostViewModel postViewModel= PostViewModel.fromCreationEvent(postCreated);

        Mono<PostViewModel> expectedMono = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .saveNewPost(Mockito.any(PostViewModel.class)))
                .thenReturn(expectedMono);

        updateViewUseCase.accept(postCreated);

        Mockito
                .verify(mongoViewRepository)
                .saveNewPost(Mockito.any(PostViewModel.class));

        Mockito
                .verify(eventBus)
                .publishPost(Mockito.any(PostViewModel.class));
    }

    @Test
    void testPostEdited() {
        // First save a normal post.
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        PostViewModel postViewModel= PostViewModel.fromCreationEvent(postCreated);

        Mono<PostViewModel> expectedMono = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .saveNewPost(Mockito.any(PostViewModel.class)))
                .thenReturn(expectedMono);

        updateViewUseCase.accept(postCreated);

        // Now perform the actual action that will be tested

        PostEdited postEdited = new PostEdited("456", "I'm revolving in my grave!");

        PostViewModel editedPost = PostViewModel.fromEditionEvent(postEdited);

        Mono<PostViewModel> expectedResult = Mono.just(editedPost);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .editPost(Mockito.any(PostViewModel.class)))
                .thenReturn(expectedResult);

        updateViewUseCase.accept(postCreated);

        updateViewUseCase.accept(postEdited);

        Mockito
                .verify(mongoViewRepository)
                .editPost(Mockito.any(PostViewModel.class));

        Mockito
                .verify(eventBus)
                .publishPostEdition(Mockito.any(PostViewModel.class));

    }

    @Test
    void testCommentAdded() {

        // Create parent post
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        PostViewModel postViewModel= PostViewModel.fromCreationEvent(postCreated);

        // Create its comment
        CommentAdded commentAdded = new CommentAdded("01", "Peter Jackson", "I agree");

        commentAdded.setAggregateRootId("456");

        CommentViewModel newComment =  CommentViewModel.fromCreationEvent(commentAdded);

        postViewModel.setComments(List.of(newComment));

        Mono<PostViewModel> expectedMono = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .saveNewComment(Mockito.any(CommentViewModel.class)))
                .thenReturn(expectedMono);


        updateViewUseCase.accept(commentAdded);

        Mockito
                .verify(mongoViewRepository)
                .saveNewComment(Mockito.any(CommentViewModel.class));

        Mockito
                .verify(eventBus)
                .publishComment(Mockito.any(CommentViewModel.class));
    }

    @Test
    void testCommentEdited() {

        // Create parent post
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        PostViewModel postViewModel= PostViewModel.fromCreationEvent(postCreated);

        // Create its comment
        CommentAdded commentAdded = new CommentAdded("01", "Peter Jackson", "I agree");

        commentAdded.setAggregateRootId("456");

        CommentViewModel newComment =  CommentViewModel.fromCreationEvent(commentAdded);

        postViewModel.setComments(List.of(newComment));

        Mono<PostViewModel> expectedMono = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .saveNewComment(Mockito.any(CommentViewModel.class)))
                .thenReturn(expectedMono);


        // Now perform the actual action that will be tested

        CommentEdited commentEdited = new CommentEdited("01", "Actually I don't");

        commentEdited.setAggregateRootId("456");

        CommentViewModel editedComment = CommentViewModel.fromEditionEvent(commentEdited);

        postViewModel.setComments(List.of(editedComment));


        Mono<PostViewModel> expectedResult = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .editComment(Mockito.any(CommentViewModel.class)))
                .thenReturn(expectedResult);

        updateViewUseCase.accept(commentAdded);
        updateViewUseCase.accept(commentEdited);


        Mockito
                .verify(mongoViewRepository)
                .editComment(Mockito.any(CommentViewModel.class));

        Mockito
                .verify(eventBus)
                .publishCommentEdition(Mockito.any(CommentViewModel.class));
    }

    @Test
    void testReactionAdded() {

        // Create parent post
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        PostViewModel postViewModel= PostViewModel.fromCreationEvent(postCreated);

        // Create its comment
        ReactionAdded reactionAdded = new ReactionAdded("01", "Amazon", "LOL");

        reactionAdded.setAggregateRootId("456");

        ReactionViewModel newReaction =  ReactionViewModel.fromCreationEvent(reactionAdded);

        postViewModel.setReactions(List.of(newReaction));

        Mono<PostViewModel> expectedMono = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .saveNewReaction(Mockito.any(ReactionViewModel.class)))
                .thenReturn(expectedMono);


        updateViewUseCase.accept(reactionAdded);

        Mockito
                .verify(mongoViewRepository)
                .saveNewReaction(Mockito.any(ReactionViewModel.class));

        Mockito
                .verify(eventBus)
                .publishReaction(Mockito.any(ReactionViewModel.class));
    }

    @Test
    void testReactionEdited() {

        // Create parent post
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        PostViewModel postViewModel= PostViewModel.fromCreationEvent(postCreated);

        // Create its reaction
        ReactionAdded reactionAdded = new ReactionAdded("01", "Amazon", "LOL");

        reactionAdded.setAggregateRootId("456");

        ReactionViewModel newReaction =  ReactionViewModel.fromCreationEvent(reactionAdded);

        postViewModel.setReactions(List.of(newReaction));

        Mono<PostViewModel> expectedMono = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .saveNewReaction(Mockito.any(ReactionViewModel.class)))
                .thenReturn(expectedMono);


        // Now perform the actual action that will be tested

        ReactionEdited reactionEdited = new ReactionEdited("01", "ANGRY");

        reactionEdited.setAggregateRootId("456");

        ReactionViewModel editedReaction = ReactionViewModel.fromEditionEvent(reactionEdited);

        // editedReaction.setPostId("456");

        postViewModel.setReactions(List.of(editedReaction));


        Mono<PostViewModel> expectedResult = Mono.just(postViewModel);

        Mockito
                .lenient()
                .when(mongoViewRepository
                        .editReaction(Mockito.any(ReactionViewModel.class)))
                .thenReturn(expectedResult);

        updateViewUseCase.accept(reactionAdded);
        updateViewUseCase.accept(reactionEdited);


        Mockito
                .verify(mongoViewRepository)
                .editReaction(Mockito.any(ReactionViewModel.class));

        Mockito
                .verify(eventBus)
                .publishReactionEdition(Mockito.any(ReactionViewModel.class));
    }



}