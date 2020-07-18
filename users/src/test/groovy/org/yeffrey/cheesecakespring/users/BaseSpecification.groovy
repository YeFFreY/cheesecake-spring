package org.yeffrey.cheesecakespring.users

import com.github.javafaker.Faker
import org.yeffrey.cheesecakespring.common.domain.UserId
import org.yeffrey.cheesecakespring.users.ports.MockUserRepositoryPort
import spock.lang.Shared
import spock.lang.Specification

class BaseSpecification extends Specification {
    @Shared
    Faker faker = new Faker(new Random(24))

    def userRepository = new MockUserRepositoryPort()

    def aUser = Optional.of(UserId.from(faker.name().username()))
    def anotherUser = Optional.of(UserId.from(faker.name().username()))

}
