package org.yeffrey.cheesecakespring.infrastructure.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.library.domain.UserId;
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserService;

import java.security.Principal;
import java.util.Optional;

@Component
public class UserService implements AuthenticatedUserService {

    @Override
    public Optional<UserId> getAuthenticatedUserId() {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName)
            .map(UserId::from);
    }
}
