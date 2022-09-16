package org.alpha.application.adapters.bus;

import lombok.extern.slf4j.Slf4j;
import org.alpha.application.config.RabbitConfig;
import org.alpha.business.gateways.EventBus;
import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
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

        log.info(String.format("%s event sent to exchange", notification.getType()));
        
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.GENERAL_ROUTING_KEY,
                notification.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {
        ErrorEvent event = new ErrorEvent(
                errorEvent.getClass().getTypeName(),
                errorEvent.getMessage()
        );

        Notification notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.GENERAL_ROUTING_KEY,
                notification.serialize().getBytes()
        );
    }
}
