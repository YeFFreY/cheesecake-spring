package org.yeffrey.cheesecakespring.activities.domain

import com.github.javafaker.Faker
import groovy.transform.CompileStatic

@CompileStatic
trait DomainSamples {
    static Faker faker = new Faker();

    static public Activity givenActivity() {
        return givenActivity(UserId.from(faker.name().username()))
    }

    static public Activity givenActivity(UserId user) {
        return Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), user)
    }

    static public Resource givenResource() {
        return givenResource(UserId.from(faker.name().username()))
    }

    static public Resource givenResource(UserId user) {
        return Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, user)
    }

}