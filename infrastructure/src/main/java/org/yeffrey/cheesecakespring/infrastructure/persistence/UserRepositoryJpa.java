package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yeffrey.cheesecakespring.users.domain.User;

import java.util.Optional;

public interface UserRepositoryJpa extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
