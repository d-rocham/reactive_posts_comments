package org.beta.application.handlers;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.beta.application.adapters.bus.Notification;
import org.beta.business.usecases.UpdateViewUseCase;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class QueueHandler implements Consumer<String> {
    // TODO: learn how to turn the domain layer into an installable module

    private final Gson gson = new Gson();
    private final UpdateViewUseCase updateViewUseCase;

    public QueueHandler(UpdateViewUseCase updateViewUseCase) {
        this.updateViewUseCase = updateViewUseCase;
    }

    @Override
    public void accept(String jsonView) {

        // Deserialize the incoming message into a notification
        Notification notification = gson.fromJson(jsonView, Notification.class);
        // Below operation could possibly be avoided if the Domain layer is turned into its own module
        String type = notification
                .getType()
                .replace("alpha", "beta");

        try {
            // Try to cast the Notification into a DomainEvent
            //(super class from which all the domain events extend from).

            // This implementation is scalable, because any future new
            // domain event will fit as long as it extends from DomainEvent
            DomainEvent receivedEvent = (DomainEvent) gson.fromJson(
                    notification.getBody(),
                    Class.forName(type));

            // Should the notification fit with the DomainEvent type, activate the updateViewUseCase
            log.info(String.format("Received %s notification", notification.getType()));
            updateViewUseCase.accept(receivedEvent);
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }
}
