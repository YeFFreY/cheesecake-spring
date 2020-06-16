package org.yeffrey.cheesecakespring.infrastructure.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.features.common.AuthenticatedUserPort;

import java.security.Principal;
import java.util.Optional;

@Component
public class AuthenticationService implements AuthenticatedUserPort {

    public Optional<String> getAuthenticatedUserId() {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName);
    }
}
