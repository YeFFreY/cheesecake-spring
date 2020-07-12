package org.yeffrey.cheesecakespring.library

import com.github.javafaker.Faker
import org.yeffrey.cheesecakespring.library.domain.UserId
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserPort
import org.yeffrey.cheesecakespring.library.ports.MockActivityRepository
import org.yeffrey.cheesecakespring.library.ports.MockLibraryRepository
import org.yeffrey.cheesecakespring.library.ports.MockResourceRepository
import spock.lang.Shared
import spock.lang.Specification

class BaseSpecification extends Specification {
    @Shared
    Faker faker = new Faker(new Random(24))

    def authenticatedService = Stub(AuthenticatedUserPort)
    def activityRepository = new MockActivityRepository()
    def resourceRepository = new MockResourceRepository()
    def libraryRepository = new MockLibraryRepository()

    def aUser = Optional.of(UserId.from(faker.name().username()))
    def anotherUser = Optional.of(UserId.from(faker.name().username()))

}
