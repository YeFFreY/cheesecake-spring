package org.yeffrey.cheesecakespring.infrastructure

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.activities.domain.DomainSamples
import org.yeffrey.cheesecakespring.activities.domain.UserId
import org.yeffrey.cheesecakespring.activities.ports.ActivityRepository
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository
import spock.lang.Shared

class ActivityResourcesRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Shared
    def faker = new Faker()

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ActivityRepository activityRepository

    @Autowired
    ResourceRepository resourceRepository

    def "given a saved activity and existing saved resource, resource added to activity link is persisted"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def activity = activityRepository.save(givenActivity(user))
            def resource = resourceRepository.save(givenResource(user))

        when: "resource added to activity"
            def qty = faker.number().randomDigitNotZero()
            activity.addResource(resource, qty)
            def result = activityRepository.save(activity)
            flushAndClear()

        then: "activity is saved and resource is associated with it and its qty"
            result.resources.size() == 1
            result.resources[0].quantity == qty
            result.resources[0].resource == resource
    }

    def "given a saved activity and existing saved resources, multiple resources added to activity link is persisted"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def activity = activityRepository.save(givenActivity(user))
            def resource = resourceRepository.save(givenResource(user))
            def resource2 = resourceRepository.save(givenResource(user))
            def resource3 = resourceRepository.save(givenResource(user))

        when: "resource added to activity"
            def qty = faker.number().randomDigitNotZero()
            activity.addResource(resource, qty)
            activity.addResource(resource2, qty)
            activity.addResource(resource3, qty)
            def result = activityRepository.save(activity)
            flushAndClear()

        then: "activity is saved and resources are associated with it"
            def activityWithResources = activityRepository.findByIdAndOwnerId(result.id, user)
            activityWithResources.isPresent()
            activityWithResources.get().resources.size() == 3
    }


    def "given a saved activity and associated resources, activity resources can be retrieved"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def activity = activityRepository.save(givenActivity(user))
            def resource = resourceRepository.save(givenResource(user))
            def resource2 = resourceRepository.save(givenResource(user))
            def resource3 = resourceRepository.save(givenResource(user))

        when: "resource added to activity"
            def qty = faker.number().randomDigitNotZero()
            activity.addResource(resource, qty)
            activity.addResource(resource2, qty)
            activity.addResource(resource3, qty)
            activityRepository.save(activity)
            flushAndClear()

        then: "activity is saved and resources are associated with it"
            def activityWithResources = resourceRepository.findAllByActivityIdAndOwnerId(activity.id, user)
            activityWithResources.size() == 3
    }

    def "given a saved activity and associated resources from a user, another user cannot retrieved the resources associated with this activity"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def activity = activityRepository.save(givenActivity(user))
            def resource = resourceRepository.save(givenResource(user))
            def resource2 = resourceRepository.save(givenResource(user))
            def resource3 = resourceRepository.save(givenResource(user))

        and: "activity associated with resources"
            activity.addResource(resource, faker.number().randomDigitNotZero())
            activity.addResource(resource2, faker.number().randomDigitNotZero())
            activity.addResource(resource3, faker.number().randomDigitNotZero())
            activityRepository.save(activity)
            flushAndClear()

        when: "another user tries to retrieve the resources of this activity"
            def activityWithResources = resourceRepository.findAllByActivityIdAndOwnerId(activity.id, UserId.from("anotherUser"))

        then: "nothing is returned"
            activityWithResources.size() == 0
    }

    def "given a saved activity and associated resources from a user, resources can be removed from the activity"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def activity = activityRepository.save(givenActivity(user))
            def resource = resourceRepository.save(givenResource(user))
            def resource2 = resourceRepository.save(givenResource(user))
            def resource3 = resourceRepository.save(givenResource(user))

        and: "activity associated with resources"
            activity.addResource(resource, faker.number().randomDigitNotZero())
            activity.addResource(resource2, faker.number().randomDigitNotZero())
            activity.addResource(resource3, faker.number().randomDigitNotZero())
            activityRepository.save(activity)
            flushAndClear()

        when: "user removes some resources of this activity"
            activity.removeResource(resource)
            activity.removeResource(resource2)
            activityRepository.save(activity)
            flushAndClear()
            def activityWithResources = resourceRepository.findAllByActivityIdAndOwnerId(activity.id, user)

        then: "resources are removed"
            activityWithResources.size() == 1
    }
}
