package org.beta.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import org.beta.business.gateways.EventBus;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

// TODO: Will there be a problem if current class is a service?
@Service
public class RabbitMqEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

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

    @Override
    public void publishPost(PostViewModel newPostModel) {

    }

    @Override
    public void publishComment(CommentViewModel newCommentModel) {

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
}
