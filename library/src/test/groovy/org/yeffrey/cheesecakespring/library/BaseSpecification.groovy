package org.yeffrey.cheesecakespring.library

import com.github.javafaker.Faker
import org.yeffrey.cheesecakespring.library.domain.UserId
import org.yeffrey.cheesecakespring.library.ports.ActivityRepository
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserService
import org.yeffrey.cheesecakespring.library.ports.ResourceRepository
import spock.lang.Shared
import spock.lang.Specification

class BaseSpecification extends Specification {
    @Shared
    Faker faker = new Faker(new Random(24))

    def authenticatedService = Stub(AuthenticatedUserService)
    def activityRepository = Stub(ActivityRepository)
    def resourceRepository = Stub(ResourceRepository)

    def aUser = Optional.of(UserId.from(faker.name().username()))
    def anotherUser = Optional.of(UserId.from(faker.name().username()))

}
