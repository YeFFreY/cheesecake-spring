package org.yeffrey.cheesecakespring.users.ports


import org.yeffrey.cheesecakespring.users.domain.User

import java.util.concurrent.atomic.AtomicLong

class MockUserRepositoryPort implements UserRepositoryPort {
    AtomicLong seq = new AtomicLong()
    Map<Long, User> db = [:]

    @Override
    Optional<User> findByUserName(String username) {
        return db.isEmpty()
                ? Optional.<User> empty()
                : Optional.ofNullable(db.find({ it.value.username == username }).value)
    }

    @Override
    void autoLogin(String userName) {
        if (!existsByUserName(userName)) {
            throw new IllegalStateException()
        }
    }

    @Override
    String encodePassword(String password) {
        return password
    }

    @Override
    User save(User user) {
        if (user.id == null) {
            user.id = seq.getAndIncrement()
        }
        db[user.id] = user
        return user
    }

    @Override
    boolean existsByEmail(String email) {
        return db.isEmpty()
                ? false
                : db.find({ it.value.email == email }).value != null
    }

    @Override
    boolean existsByUserName(String username) {
        return this.findByUserName(username).isPresent()
    }
}
