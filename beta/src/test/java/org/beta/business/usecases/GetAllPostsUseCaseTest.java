package org.beta.business.usecases;

import org.beta.application.adapters.repository.MongoViewRepository;
import org.beta.business.gateways.ViewRepository;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllPostsUseCaseTest {

    @Mock
    MongoViewRepository mongoViewRepository;

    GetAllPostsUseCase getAllPostsUseCase;

    @BeforeEach
    void init() {
        getAllPostsUseCase = new GetAllPostsUseCase(mongoViewRepository);
    }

    @Test
    void getAllPostsUseCaseTest() {

        // I refactored ViewModel construction, so that they are only built either from
        // edition or creation events.
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");


        Flux<PostViewModel> expectedFlux = Flux.just(
                PostViewModel.fromCreationEvent(postCreated)
        );

        Mockito
                .when(this.mongoViewRepository.findAllPosts())
                .thenReturn(expectedFlux);

        var useCaseExecuted = getAllPostsUseCase.getAllPosts();

        StepVerifier
                .create(useCaseExecuted)
                .expectNextMatches(postViewModel -> postViewModel
                        .getTitle()
                        .equals("I don't like Rings of Power")
                )
                .verifyComplete();

        Mockito.verify(mongoViewRepository).findAllPosts();
    }

}