package org.beta.application.adapters.repository;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class MongoViewRepository implements ViewRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final Gson gson = new Gson();

    private static Query generateQuery(String targetValue) {
        return new Query(Criteria
                .where("aggregateId")
                .is(targetValue));
    }

    private static void logError(Throwable error) {
        log.error(error.getMessage());
    }

    private static void logSuccessfulOperation(String successMessage) {
        log.info(successMessage);
    }


    public MongoViewRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<PostViewModel> findByAggregateId(String aggregateId) {
        Query query = generateQuery(aggregateId);

        return reactiveMongoTemplate
                .findOne(query, PostViewModel.class)
                .switchIfEmpty(Mono.error(new IllegalAccessException("Resource with requested id was not found")))
                .doOnError(error -> logError(error))
                .doOnSuccess(e -> logSuccessfulOperation("Resource fetched successfully"));

    }

    @Override
    public Flux<PostViewModel> findAllPosts() {
        return reactiveMongoTemplate
                .findAll(PostViewModel.class)
                .doOnComplete(() -> logSuccessfulOperation("Resources fetched from database."))
                .doOnError(MongoViewRepository::logError);
    }

    @Override
    public Mono<PostViewModel> saveNewPost(PostViewModel newPost) {
        return reactiveMongoTemplate
                .save(newPost)
                .doOnError(MongoViewRepository::logError)
                .doOnSuccess(e -> logSuccessfulOperation(String.format("Resource %s created successfully", e.getAggregateId())));
    }

    @Override
    public Mono<PostViewModel> saveNewComment(CommentViewModel newComment) {
        Query query = generateQuery(newComment.getPostId());

        Update update = new Update();

        return reactiveMongoTemplate
                // Find parent post
                .findOne(query, PostViewModel.class)
                .switchIfEmpty(Mono.error(new IllegalAccessException("Resource with requested id was not found")))
                .doOnError(error -> logError(error))
                .flatMap(postViewModel -> {
                    // Modify the comment list in server
                    List<CommentViewModel> updatedCommentList = postViewModel.getComments();
                    updatedCommentList.add(newComment);
                    update.set("comments", updatedCommentList);

                    // Send the modification instructions to Mongo
                    return reactiveMongoTemplate
                            .findAndModify(query, update, PostViewModel.class);
                })
                .doOnSuccess(e -> logSuccessfulOperation(String.format("Comment successfully added to resource %s", e.getAggregateId())));
    }

    @Override
    public Mono<PostViewModel> saveNewReaction(ReactionViewModel newReaction) {
        Query query = generateQuery(newReaction.getPostId());

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
    @Override
    public Mono<PostViewModel> editPost(PostViewModel editedPost) {
        Query query = generateQuery(editedPost.getAggregateId());

        Update update = new Update();

        return reactiveMongoTemplate
                .findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                    update.set("title", editedPost.getTitle());
                    return reactiveMongoTemplate
                            .findAndModify(query, update, PostViewModel.class);
                });
    }

    @Override
    public Mono<PostViewModel> editComment(CommentViewModel editedComment) {
        Query query = generateQuery(editedComment.getPostId());

        Update update = new Update();

        return reactiveMongoTemplate
                .findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                            List<CommentViewModel> comments = postViewModel.getComments();
                               /*
                                comments.forEach((comment) ->
                                        Objects.equals(comment.getId(), editedComment.getId())
                                            ? comment.setContent(editedComment.getContent())
                                            : comment;
                                );

                                // Alternative: find index of target comment in `comments` list.
                                // Use then `comments.set(index, new PostViewModel())
                                // But I want to find a way where there's no need to create the whole CVM from scratch

                                */

                            CommentViewModel targetComment = comments.stream()
                                    .filter(comment -> Objects.equals(
                                            comment.getId(),
                                            editedComment.getId()))
                                    .collect(Collectors.toList())
                                    .get(0);

                            comments.set(
                                    comments.indexOf(targetComment),
                                    editedComment);

                            update.set("comments", comments);

                            return reactiveMongoTemplate
                                    .findAndModify(query, update, PostViewModel.class);
                        }
                );

    }

    @Override
    public Mono<PostViewModel> editReaction(ReactionViewModel editedReaction) {
        Query query = new Query(Criteria
                .where("aggregateId")
                .is(editedReaction.getPostId()));

        Update update = new Update();

        return reactiveMongoTemplate
                .findOne(query, PostViewModel.class)
                .flatMap(postViewModel -> {
                            List<ReactionViewModel> reactions = postViewModel.getReactions();

                            ReactionViewModel targetReaction = reactions.stream()
                                    .filter(reaction -> Objects.equals(
                                            reaction.getId(),
                                            editedReaction.getId()))
                                    .collect(Collectors.toList())
                                    .get(0);

                            reactions.set(
                                    reactions.indexOf(targetReaction),
                                    editedReaction);

                            update.set("reactions", reactions);

                            return reactiveMongoTemplate
                                    .findAndModify(query, update, PostViewModel.class);
                        }
                );
    }
}
