package org.beta.business.gateways;

import co.com.sofka.domain.generic.DomainEvent;
import org.beta.business.gateways.model.CommentViewModel;
import org.beta.business.gateways.model.PostViewModel;
import org.beta.business.gateways.model.ReactionViewModel;

public interface EventBus {

    void publish(DomainEvent event);
    void publishError(Throwable errorEvent);

    void publishPost(PostViewModel newPostModel);

    void publishComment(CommentViewModel newCommentModel);

    void publishReaction(ReactionViewModel newReactionModel);

    void publishPostEdition(PostViewModel editedPostModel);

    void publishCommentEdition(CommentViewModel editedCommentModel);

    void publishReactionEdition(ReactionViewModel editedReactionModel);

}
