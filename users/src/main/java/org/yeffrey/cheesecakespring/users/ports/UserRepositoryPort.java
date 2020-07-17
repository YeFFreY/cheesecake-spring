package org.yeffrey.cheesecakespring.users.ports;

import org.yeffrey.cheesecakespring.users.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUserName(String username);

    void autoLogin(String userName);

    String encodePassword(String password);

    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);
}
