package org.yeffrey.cheesecakespring.activities

import com.github.javafaker.Faker
import org.yeffrey.cheesecakespring.activities.domain.UserId
import org.yeffrey.cheesecakespring.activities.ports.AuthenticatedUserService
import spock.lang.Shared
import spock.lang.Specification

class BaseSpecification extends Specification {
    @Shared
    Faker faker = new Faker(new Random(24))

    def authenticatedService = Stub(AuthenticatedUserService)
    def aUser = Optional.of(UserId.from(faker.name().username()))
    def anotherUser = Optional.of(UserId.from(faker.name().username()))

}
