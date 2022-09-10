package org.beta.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import org.beta.application.config.RabbitConfig;
import org.beta.business.gateways.EventBus;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.beta.business.gateways.model.ViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void convertAndSendToTemplate(String routingKey, ViewModel viewModelToSend) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                routingKey,
                gson.toJson(viewModelToSend).getBytes()
        );
    }

    @Override
    public void publishPost(PostViewModel newPostModel) {
        convertAndSendToTemplate(RabbitConfig.PROXY_ROUTING_KEY_POST_CREATED, newPostModel);
    }

    @Override
    public void publishComment(CommentViewModel newCommentModel) {
        convertAndSendToTemplate(RabbitConfig.PROXY_ROUTING_KEY_COMMENT_ADDED, newCommentModel);
    }

    @Override
    public void publishReaction(ReactionViewModel newReactionModel) {
        convertAndSendToTemplate(RabbitConfig.PROXY_ROUTING_KEY_REACTION_ADDED, newReactionModel);
    }

    @Override
    public void publishPostEdition(PostViewModel editedPostModel) {
        convertAndSendToTemplate(RabbitConfig.PROXY_ROUTING_KEY_POST_EDITED, editedPostModel);
    }

    @Override
    public void publishCommentEdition(CommentViewModel editedCommentModel) {
        convertAndSendToTemplate(RabbitConfig.PROXY_QUEUE_COMMENT_EDITED, editedCommentModel);
    }

    @Override
    public void publishReactionEdition(ReactionViewModel editedReactionModel) {
        convertAndSendToTemplate(RabbitConfig.PROXY_ROUTING_KEY_REACTION_EDITED, editedReactionModel);
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
