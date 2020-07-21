package org.yeffrey.cheesecakespring.common.event;

public interface EventPublisher {
    void publish(DomainEvent event);
}
