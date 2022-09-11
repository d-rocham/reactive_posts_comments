package org.gamma.application.bus;

import com.google.gson.Gson;
import org.gamma.application.bus.model.CommentModel;
import org.gamma.application.bus.model.PostModel;
import org.gamma.application.controller.SocketController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    private final Gson gson = new Gson();
    private final SocketController socketController;

    public RabbitMQConsumer(SocketController socketController) {
        this.socketController = socketController;
    }

    /*QUEUES TO LISTEN TO*/
    public static final String PROXY_QUEUE_POST_CREATED = "events.proxy.post.created";
    public static final String PROXY_QUEUE_COMMENT_ADDED = "events.proxy.comment.added";
    public static final String PROXY_QUEUE_REACTION_ADDED = "events.proxy.reaction.added";

    // TODO: listen to queues below
    public static final String PROXY_QUEUE_POST_EDITED = "events.proxy.post.edited";
    public static final String PROXY_QUEUE_COMMENT_EDITED = "events.proxy.comment.edited";
    public static final String PROXY_QUEUE_REACTION_EDITED = "events.proxy.reaction.edited";

    @RabbitListener(queues = PROXY_QUEUE_POST_CREATED)
    public void listenToPostCreation(String json) {
        PostModel newPost = gson.fromJson(json, PostModel.class);
        socketController.submitPostCreated("mainSpace", newPost);
    }

    @RabbitListener(queues = PROXY_QUEUE_COMMENT_ADDED)
    public void listenToCommentCreation(String json) {
        CommentModel newComment = gson.fromJson(json, CommentModel.class);
        socketController.submitCommentAdded(newComment.getPostId(), newComment);
    }

    // TODO: add listener for remaining events. Check first if current application data flow
    //  in front end is incompatible w. reactions & editions
}
