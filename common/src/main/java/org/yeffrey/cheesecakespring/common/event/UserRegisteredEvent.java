package org.yeffrey.cheesecakespring.common.event;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserRegisteredEvent implements DomainEvent {
    private String username;

    private UserRegisteredEvent(String username) {
        this.username = username;
    }

    public static UserRegisteredEvent from(String username) {
        checkNotNull(username);
        return new UserRegisteredEvent(username);
    }

    public String getUsername() {
        return username;
    }
}
