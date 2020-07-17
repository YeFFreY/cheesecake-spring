package org.yeffrey.cheesecakespring.library.domain

import com.github.javafaker.Faker
import groovy.transform.CompileStatic
import org.yeffrey.cheesecakespring.common.domain.UserId

@CompileStatic
trait DomainSamples {
    static Faker faker = new Faker()

    static Library givenLibrary(UserId userId) {
        return Library.from(userId)
    }

    static Activity givenActivity(Library library = null) {
        return Activity.from(library, ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()))
    }

    static Resource givenResource(Library library = null) {
        return Resource.from(library, ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item)
    }

}