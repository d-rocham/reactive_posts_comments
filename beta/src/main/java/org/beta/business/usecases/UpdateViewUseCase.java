package org.beta.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import org.beta.business.gateways.EventBus;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent> {

    //private final EventBus bus;
    private final ViewUpdater viewUpdater;

    private final BusPublisher busPublisher;

    public UpdateViewUseCase(/*EventBus bus,*/ ViewUpdater viewUpdater, BusPublisher busPublisher) {
        // this.bus = bus;
        this.viewUpdater = viewUpdater;
        this.busPublisher = busPublisher;
    }

    @Override
    public void accept(DomainEvent event) {
        // TODO: below line would be BusPublisher.applyEvent(event) instead of bus.publish()

        // bus.publish(event);
        viewUpdater.applyEvent(event);
        busPublisher.applyEvent(event);

    }
}
