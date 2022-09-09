package org.beta.application.adapters.repository;

import co.com.sofka.domain.generic.DomainEvent;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class MongoViewRepository implements ViewRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final Gson gson = new Gson();

    /* TODO: Figure out why the wildcard below doesn't work
    private static Query queryGenerator(<? extends DomainEvent> domainEvent) {
        return new Query(Criteria
                .where("aggregateID")
                //.is(domainEvent.get...))
    }
    */

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
    @Override
    public Mono<PostViewModel> editPost(PostViewModel editedPost) {
        // TODO: query below is repeated accross methods
        // TODO: Create a fuction / to generate a query: it takes as an argument the domain event, returns query

        Query query = new Query(Criteria
                .where("aggregateId")
                .is(editedPost.getAggregateId()));

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
        Query query = new Query(Criteria
                .where("aggregateId")
                .is(editedComment.getPostId()));

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
