package org.yeffrey.cheesecakespring.activities;

import org.yeffrey.cheesecakespring.activities.domain.UserId;

import java.util.Optional;

public interface AuthenticatedUserPort {
    Optional<UserId> getAuthenticatedUserId();
}
