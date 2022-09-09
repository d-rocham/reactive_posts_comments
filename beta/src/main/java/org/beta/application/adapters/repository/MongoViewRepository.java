package org.beta.application.adapters.repository;

import com.google.gson.Gson;
import org.beta.business.gateways.ViewRepository;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return reactiveMongoTemplate
                .save(newPost);
    }

    @Override
    public Mono<PostViewModel> saveNewComment(CommentViewModel newComment) {
        Query query = new Query(Criteria
                .where("aggregateId")
                .is(newComment.getPostId()));

        Update update = new Update();

        return reactiveMongoTemplate
                // Find parent post
                .findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                    // Modify the comment list in server
                    List<CommentViewModel> updatedCommentList = postViewModel.getComments();
                    updatedCommentList.add(newComment);
                    update.set("comments", updatedCommentList);

                    // Send the modification instructions to Mongo
                    return reactiveMongoTemplate
                            .findAndModify(query, update, PostViewModel.class);
                });
    }

    @Override
    public Mono<PostViewModel> saveNewReaction(ReactionViewModel newReaction) {
        Query query = new Query(Criteria
                .where("aggregateId")
                .is(newReaction.getPostId()));

        Update update = new Update();

        return reactiveMongoTemplate
                .findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                    List<ReactionViewModel> updatedReactionList = postViewModel.getReactions();
                    updatedReactionList.add(newReaction);
                    update.set("reactions", updatedReactionList);

                    return reactiveMongoTemplate
                            .findAndModify(query, update, PostViewModel.class);
                });
    }

    // TODO: Implement secondary methods below.

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
