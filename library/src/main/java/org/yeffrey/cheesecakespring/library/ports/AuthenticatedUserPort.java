package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.common.domain.UserId;

import java.util.Optional;

public interface AuthenticatedUserPort {
    Optional<UserId> findAuthenticatedUserId();
}
