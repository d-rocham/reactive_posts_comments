package org.beta.application.config;

import org.beta.application.handlers.QueueHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "core-posts";
    public static final String GENERAL_QUEUE = "events.general";

    /* QUEUES */
    public static final String PROXY_QUEUE_POST_CREATED = "events.proxy.post.created";
    public static final String PROXY_QUEUE_COMMENT_ADDED = "events.proxy.comment.added";
    public static final String PROXY_QUEUE_REACTION_ADDED = "events.proxy.reaction.added";
    public static final String PROXY_QUEUE_POST_EDITED = "events.proxy.post.edited";
    public static final String PROXY_QUEUE_COMMENT_EDITED = "events.proxy.comment.edited";
    public static final String PROXY_QUEUE_REACTION_EDITED = "events.proxy.reaction.edited";

    /* KEYS */
    public static final String PROXY_ROUTING_KEY_POST_CREATED = "routingKey.proxy.post.created";
    public static final String PROXY_ROUTING_KEY_COMMENT_ADDED = "routingKey.proxy.comment.added";
    public static final String PROXY_ROUTING_KEY_REACTION_ADDED = "routingKey.proxy.reaction.added";
    public static final String PROXY_ROUTING_KEY_POST_EDITED = "routingKey.proxy.post.edited";
    public static final String PROXY_ROUTING_KEY_COMMENT_EDITED = "routingKey.proxy.comment.edited";
    public static final String PROXY_ROUTING_KEY_REACTION_EDITED = "routingKey.proxy.reaction.edited";


    @Autowired
    private QueueHandler queueHandler;

    /* CREATE QUEUES */

    @Bean
    public Queue postCreatedQueue(){
        return new Queue(PROXY_QUEUE_POST_CREATED);
    }

    @Bean
    public Queue commentAddedQueue(){
        return new Queue(PROXY_QUEUE_COMMENT_ADDED);
    }

    @Bean
    public Queue reactionAddedQueue() {
        return new Queue(PROXY_QUEUE_REACTION_ADDED);
    }

    @Bean
    public Queue postEditedQueue() {
        return new Queue(PROXY_QUEUE_POST_EDITED);
    }

    @Bean
    public Queue commentEditedQueue() {
        return new Queue(PROXY_QUEUE_COMMENT_EDITED);
    }

    @Bean
    public Queue reactionEditedQueue() {
        return new Queue(PROXY_QUEUE_REACTION_EDITED);
    }

    @Bean
    public TopicExchange getTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    /* BIND QUEUES TO EXCHANGE */

    @Bean
    public Binding BindingToPostCreatedQueue() {
        return BindingBuilder
                .bind(postCreatedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_POST_CREATED);
    }

    @Bean
    public Binding BindingToCommentAdded() {
        return BindingBuilder
                .bind(commentAddedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_COMMENT_ADDED);
    }

    @Bean
    public Binding BindingToReactionAdded() {
        return BindingBuilder
                .bind(reactionAddedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_REACTION_ADDED);
    }

    @Bean
    public Binding BindingToPostEdited() {
        return BindingBuilder
                .bind(postEditedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_POST_EDITED);
    }

    @Bean
    public Binding BindingToCommentEdited() {
        return BindingBuilder
                .bind(commentEditedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_COMMENT_EDITED);
    }

    @Bean
    public Binding BindingToReactionEdited() {
        return BindingBuilder
                .bind(reactionEditedQueue())
                .to(getTopicExchange())
                .with(PROXY_ROUTING_KEY_REACTION_EDITED);
    }

    // Queue that receives messages from Alpha microservice
    @RabbitListener(queues = GENERAL_QUEUE)
    public void generalQueueListener(String received){
        queueHandler.accept(received);
    }
}
