package application.adapters.bus;

import application.config.RabbitConfig;
import business.gateways.EventBus;
import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

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
