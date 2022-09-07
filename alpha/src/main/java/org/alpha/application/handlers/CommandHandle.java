package org.alpha.application.handlers;

import co.com.sofka.domain.generic.DomainEvent;
import org.alpha.business.usecases.*;
import org.alpha.domain.commands.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CommandHandle {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase createPostUseCase) {
        return route(
                POST("/createPost")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                createPostUseCase.apply(
                                        request.bodyToMono(CreatePost.class)),
                                DomainEvent.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addComment(AddCommentUseCase addCommentUseCase) {
        return route(
                POST("/addComment")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse
                        .ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                addCommentUseCase.apply(
                                        request.bodyToMono(AddComment.class)),
                                DomainEvent.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addReaction(AddReactionUseCase addReactionUseCase) {
        return route(
                POST("/addReaction/")
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
                POST("/editComment/")
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
                POST("/editPost/")
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
                POST("/editReaction/")
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
