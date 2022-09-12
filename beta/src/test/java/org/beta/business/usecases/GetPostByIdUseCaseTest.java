package org.beta.business.usecases;

import org.beta.application.adapters.repository.MongoViewRepository;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.domain.events.PostCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetPostByIdUseCaseTest {

    @Mock
    MongoViewRepository mongoViewRepository;

    GetPostByIdUseCase getPostByIdUseCase;

    @BeforeEach
    void init() {
        getPostByIdUseCase = new GetPostByIdUseCase(mongoViewRepository);
    }

    @Test
    void getPostByIdUseCaseTest() {

        // I refactored ViewModel construction, so that they are only built either from
        // edition or creation events.
        PostCreated postCreated = new PostCreated(
                "I don't like Rings of Power",
                "JRR Tolkien"
        );

        postCreated.setAggregateRootId("456");

        Mono<PostViewModel> expectedMono = Mono.just(
                PostViewModel.fromCreationEvent(postCreated)
        );

        Mockito
                .when(mongoViewRepository
                        .findByAggregateId(Mockito.anyString()))
                .thenReturn(expectedMono);

        var useCaseExecuted = getPostByIdUseCase.getPostById("456");

        StepVerifier
                .create(useCaseExecuted)
                .expectNextMatches(postViewModel -> postViewModel
                        .getTitle()
                        .equals("I don't like Rings of Power")
                )
                .verifyComplete();

        Mockito.verify(mongoViewRepository).findByAggregateId(Mockito.anyString());



    }


}