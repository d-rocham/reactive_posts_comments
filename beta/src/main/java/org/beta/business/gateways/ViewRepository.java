package org.beta.business.gateways;

import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ViewRepository {


    Mono<PostViewModel> findByAggregateId(String aggregateId);

    Flux<PostViewModel> findAllPosts();

    Mono<PostViewModel> saveNewPost(PostViewModel newPost);

    // Why does it return a PostViewModel instead of a CommentViewModel
    Mono<PostViewModel> saveNewComment(CommentViewModel newComment);

    Mono<PostViewModel> saveNewReaction (ReactionViewModel newReaction);

    Mono<PostViewModel> editPost(PostViewModel editedPost);

    Mono<PostViewModel> editComment(CommentViewModel editedComment);

    Mono<PostViewModel> editReaction(ReactionViewModel editedReaction);

}
