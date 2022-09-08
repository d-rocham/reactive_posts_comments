package org.beta.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import org.beta.business.gateways.EventBus;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent> {

    private final EventBus bus;
    private final ViewUpdater viewUpdater;

    public UpdateViewUseCase(EventBus bus, ViewUpdater viewUpdater) {
        this.bus = bus;
        this.viewUpdater = viewUpdater;
    }

    @Override
    public void accept(DomainEvent event) {
        bus.publish(event);
        viewUpdater.applyEvent(event);

    }
}
