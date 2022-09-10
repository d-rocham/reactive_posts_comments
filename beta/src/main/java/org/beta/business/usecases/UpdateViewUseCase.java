package org.beta.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class UpdateViewUseCase implements Consumer<DomainEvent> {

    private final ViewUpdater viewUpdater;

    private final BusPublisher busPublisher;

    public UpdateViewUseCase(ViewUpdater viewUpdater, BusPublisher busPublisher) {
        this.viewUpdater = viewUpdater;
        this.busPublisher = busPublisher;
    }

    @Override
    public void accept(DomainEvent event) {
        viewUpdater.applyEvent(event);
        busPublisher.applyEvent(event);

    }
}
