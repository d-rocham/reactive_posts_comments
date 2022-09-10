package org.beta.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import org.beta.application.config.RabbitConfig;
import org.beta.business.gateways.EventBus;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishPost(PostViewModel newPostModel) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.PROXY_ROUTING_KEY_POST_CREATED,
                gson.toJson(newPostModel).getBytes()
        );
    }

    @Override
    public void publishComment(CommentViewModel newCommentModel) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.PROXY_ROUTING_KEY_COMMENT_ADDED,
                gson.toJson(newCommentModel).getBytes()
        );
    }

    @Override
    public void publishReaction(ReactionViewModel newReactionModel) {

    }

    @Override
    public void publishPostEdition(PostViewModel editedPostModel) {

    }

    @Override
    public void publishCommentEdition(CommentViewModel editedCommentModel) {

    }

    @Override
    public void publishReacctionEdition(ReactionViewModel editedReactionModel) {

    }

    // Are the methods below used, or just examples to follow?

    @Override
    public void publish(DomainEvent event) {
        Notification notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
