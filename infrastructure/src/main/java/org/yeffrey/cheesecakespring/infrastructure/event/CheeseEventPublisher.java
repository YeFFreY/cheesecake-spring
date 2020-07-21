package org.yeffrey.cheesecakespring.infrastructure.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.common.event.DomainEvent;
import org.yeffrey.cheesecakespring.common.event.EventPublisher;

@Component
public class CheeseEventPublisher implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public CheeseEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        this.applicationEventPublisher.publishEvent(event);
    }
}
