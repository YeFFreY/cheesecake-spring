package org.yeffrey.cheesecakespring.infrastructure.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter;
import org.yeffrey.cheesecakespring.infrastructure.persistence.UserRepositoryJpa;
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserPort;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@PortAdapter
@Component
public class UserService implements AuthenticatedUserPort, UserDetailsService {
    private final UserRepositoryJpa userRepositoryJpa;

    public UserService(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Optional<UserId> findAuthenticatedUserId() {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Principal::getName)
            .map(UserId::from);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRepositoryJpa.findByUsername(username)
            .map(u -> new User(u.getUsername(), u.getPassword(), new ArrayList<>()))
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
