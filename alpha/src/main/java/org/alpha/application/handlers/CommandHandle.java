package org.alpha.application.handlers;

import co.com.sofka.domain.generic.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.alpha.business.usecases.*;
import org.alpha.domain.commands.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class CommandHandle {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase createPostUseCase) {
        return route(
                POST("/createPost").and(accept(MediaType.APPLICATION_JSON)),
                request -> createPostUseCase.apply(request.bodyToMono(CreatePost.class))
                        .collectList()
                        .flatMap(domainEvents -> {
                            log.info("Post created ");
                            return ServerResponse.ok().
                                    contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(domainEvents);
                                }

                        )
                        .onErrorResume(error -> {
                            log.error(error.getMessage());
                            return ServerResponse.badRequest().build();
                        })

                        /*ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                createPostUseCase.apply(
                                        request.bodyToMono(CreatePost.class)),
                                DomainEvent.class
                        )) */
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addComment(AddCommentUseCase addCommentUseCase) {
        return route(
                POST("/addComment")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> addCommentUseCase.apply(request.bodyToMono(AddComment.class))
                        .collectList()
                        .flatMap(domainEvents -> {
                            log.info("Comment added");
                            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(domainEvents);
                        })
                        .onErrorResume(error -> {
                            log.error(error.getMessage() + "You missed a key, or the resource was not found");
                            return ServerResponse.badRequest().build();
                        })


                        /*ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                addCommentUseCase.apply(
                                        request.bodyToMono(AddComment.class)),
                                DomainEvent.class
                        )) */
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addReaction(AddReactionUseCase addReactionUseCase) {
        return route(
                POST("/addReaction")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                addReactionUseCase.apply(
                                        request.bodyToMono(AddReaction.class)),
                                DomainEvent.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> editComment(EditCommentUseCase editCommentUseCase) {
        return route(
                PATCH("/editComment")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                editCommentUseCase.apply(
                                        request.bodyToMono(EditComment.class)),
                                DomainEvent.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> editPost(EditPostUseCase editPostUseCase) {
        return route(
                PATCH("/editPost")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                editPostUseCase.apply(
                                        request.bodyToMono(EditPost.class)),
                                DomainEvent.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> editReaction(EditReactionUseCase editReactionUseCase) {
        return route(
                PATCH("/editReaction")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                editReactionUseCase.apply(
                                        request.bodyToMono(EditReaction.class)),
                                DomainEvent.class
                        ))
        );
    }
}
