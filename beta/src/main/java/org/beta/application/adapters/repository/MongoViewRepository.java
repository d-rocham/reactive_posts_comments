package org.beta.application.adapters.repository;

import com.google.gson.Gson;
import org.beta.business.gateways.ViewRepository;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoViewRepository implements ViewRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final Gson gson = new Gson();

    public MongoViewRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        Query query = new Query(Criteria
                .where("aggregateId")
                .is(aggregateId));

        return reactiveMongoTemplate
                .findOne(query, PostViewModel.class);
    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        return reactiveMongoTemplate
                .findAll(PostViewModel.class);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel newPost) {
        return null;
    }

    @Override
    public Mono<PostViewModel> saveNewComment(CommentViewModel newComment) {
        return null;
    }

    @Override
    public Mono<PostViewModel> saveNewReaction(ReactionViewModel newReaction) {
        return null;
    }

    @Override
    public Mono<PostViewModel> editPost(PostViewModel editedPost) {
        return null;
    }

    @Override
    public Mono<PostViewModel> editComment(CommentViewModel editedComment) {
        return null;
    }

    @Override
    public Mono<PostViewModel> editReaction(ReactionViewModel editedReaction) {
        return null;
    }
}
