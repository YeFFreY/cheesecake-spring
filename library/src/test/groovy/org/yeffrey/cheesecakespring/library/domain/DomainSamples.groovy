package org.yeffrey.cheesecakespring.library.domain

import com.github.javafaker.Faker
import groovy.transform.CompileStatic

@CompileStatic
trait DomainSamples {
    static Faker faker = new Faker()

    static Activity givenActivity() {
        return Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()))
    }

    static Resource givenResource() {
        return Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item)
    }

}