package org.yeffrey.cheesecakespring.features

import com.github.javafaker.Faker
import org.yeffrey.cheesecakespring.features.common.AuthenticatedUserPort
import spock.lang.Shared
import spock.lang.Specification

class BaseSpecification extends Specification {
    @Shared
    Faker faker = new Faker(new Random(24))

    def authenticatedService = Stub(AuthenticatedUserPort)
    def aUser = Optional.of(faker.name().username())
    def anotherUser = Optional.of(faker.name().username())

}
