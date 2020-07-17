package org.yeffrey.cheesecakespring.users.stories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.users.domain.User;
import org.yeffrey.cheesecakespring.users.dto.RegisterCommand;
import org.yeffrey.cheesecakespring.users.ports.UserRepositoryPort;

@Service
@Transactional(readOnly = true)
public class UserStories {
    private final UserRepositoryPort userRepositoryPort;

    public UserStories(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Transactional
    public User register(final RegisterCommand command) {
        if (this.userAlreadyRegistered(command)) {
            return null;
        }

        User newUser = User.from(command.getUsername(), this.userRepositoryPort.encodePassword(command.getPassword()), command.getEmail());
        return this.userRepositoryPort.save(newUser);
    }

    private boolean userAlreadyRegistered(RegisterCommand command) {
        return this.userRepositoryPort.existsByUserName(command.getUsername()) || this.userRepositoryPort.existsByEmail(command.getEmail());
    }

    public void autoLogin(String userName) {
        this.userRepositoryPort.autoLogin(userName);
    }
}
