package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.domain.UserId;

import java.util.Optional;

public interface AuthenticatedUserService {
    Optional<UserId> getAuthenticatedUserId();
}
