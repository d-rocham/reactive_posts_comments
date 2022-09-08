package org.beta.application.handlers;

import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.usecases.GetAllPostsUseCase;
import org.beta.business.usecases.GetPostByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QueryHandler {

    @Bean
    public RouterFunction<ServerResponse> findAllPosts(GetAllPostsUseCase getAllPostsUseCase) {
        return route(
                GET("/getAllPosts"),
                request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                getAllPostsUseCase.getAllPosts(),
                                PostViewModel.class))
                        .onErrorResume(throwable ->
                                ServerResponse.status(HttpStatus.NOT_FOUND).build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> findPostById(GetPostByIdUseCase getPostByIdUseCase) {
        return route(
                GET("/getPost/{postId}"),
                request -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                getPostByIdUseCase.getPostById(request.pathVariable("postId")),
                                PostViewModel.class
                        ))
                        .onErrorResume(throwable ->
                                ServerResponse.status(HttpStatus.NOT_FOUND).build())
        );
    }
}
