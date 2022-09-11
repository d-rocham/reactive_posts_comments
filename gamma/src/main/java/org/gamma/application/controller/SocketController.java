package org.gamma.application.controller;

import com.google.gson.Gson;
import org.gamma.application.bus.model.CommentModel;
import org.gamma.application.bus.model.PostModel;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@ServerEndpoint("/retrieve/{correlationId}")
public class SocketController {

    private static final Logger logger = Logger.getLogger(SocketController.class.getName());
    private static Map<String, Map<String, Session>> sessions;
    private final Gson gson = new Gson();

    public SocketController() {
        if (Objects.isNull(sessions)) {
            sessions = new ConcurrentHashMap<>();
        }
    }

    private static void broadcastJSON(String JSONMessage, String targetCorrelationId) {
        logger.info(String.format("Sent from %s", targetCorrelationId));

        // Broadcast the message for each active session associated with the requested post
        sessions.get(targetCorrelationId)
                .values().forEach(session -> {
                    try {
                        session.getAsyncRemote().sendText(JSONMessage);
                    } catch (RuntimeException runtimeException) {
                        logger.log(Level.SEVERE, runtimeException.getMessage(), runtimeException);
                    }
                });
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("correlationId") String correlationId) {

        logger.info(String.format("Connected from %s", correlationId));
        Map<String, Session> sessionMap = sessions.getOrDefault(correlationId, new HashMap<>());

        sessionMap.put(session.getId(), session);
        sessions.put(correlationId, sessionMap);
    }

    @OnClose
    public void onClose(Session session, @PathParam("correlationId") String correlationId) {
        sessions
                .get(correlationId)
                .remove(session.getId());

        logger.info(String.format("Disconnected by %s", correlationId));
    }

    @OnError
    public void onError(Session session, @PathParam("correlationId") String correlationId, Throwable throwable) {
        sessions
                .get(correlationId)
                .remove(session.getId());

        logger.log(Level.SEVERE, throwable.getMessage());

    }

    public void submitPostCreated(String correlationId, PostModel newPost) {
        if (Objects.nonNull(correlationId) && sessions.containsKey(correlationId)) {
            broadcastJSON(correlationId, gson.toJson(newPost));
        }
    }

    public void submitCommentAdded(String correlationId, CommentModel newComment) {
        if (Objects.nonNull(correlationId) && sessions.containsKey(correlationId)) {
            broadcastJSON(correlationId, gson.toJson(newComment));
        }
    }

    // TODO: Would the above logic change for entity edition or reaction adding?
}
