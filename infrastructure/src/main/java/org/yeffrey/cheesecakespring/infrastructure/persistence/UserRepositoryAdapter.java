package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter;
import org.yeffrey.cheesecakespring.users.domain.User;
import org.yeffrey.cheesecakespring.users.ports.UserRepositoryPort;

import java.util.ArrayList;
import java.util.Optional;

@PortAdapter
@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryJpa userRepositoryJpa;

    public UserRepositoryAdapter(PasswordEncoder passwordEncoder,
                                 UserRepositoryJpa userRepositoryJpa) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return this.userRepositoryJpa.findByUsername(username);
    }

    @Override
    public void autoLogin(String userName) {
        final Authentication auth = this.findByUserName(userName)
            .map(u -> new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), new ArrayList<>()))
            .map(u -> new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities()))
            .orElseThrow(() -> new AccessDeniedException(userName));

        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    @Override
    public String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Override
    public User save(User user) {
        return this.userRepositoryJpa.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepositoryJpa.existsByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return this.userRepositoryJpa.existsByUsername(userName);
    }
}
