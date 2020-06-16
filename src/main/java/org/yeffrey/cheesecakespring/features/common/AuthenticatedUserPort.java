package org.yeffrey.cheesecakespring.features.common;

import java.util.Optional;

public interface AuthenticatedUserPort {
    Optional<String> getAuthenticatedUserId();
}
