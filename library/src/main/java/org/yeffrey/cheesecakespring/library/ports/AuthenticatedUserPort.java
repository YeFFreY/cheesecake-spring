package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.library.domain.UserId;

import java.util.Optional;

public interface AuthenticatedUserPort {
    Optional<UserId> findAuthenticatedUserId();
}
