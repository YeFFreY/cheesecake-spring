package org.yeffrey.cheesecakespring.infrastructure.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter;
import org.yeffrey.cheesecakespring.library.domain.UserId;
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserPort;

import java.security.Principal;
import java.util.Optional;

@PortAdapter
@Component
public class UserService implements AuthenticatedUserPort {

    @Override
    public Optional<UserId> findAuthenticatedUserId() {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName)
            .map(UserId::from);
    }
}
