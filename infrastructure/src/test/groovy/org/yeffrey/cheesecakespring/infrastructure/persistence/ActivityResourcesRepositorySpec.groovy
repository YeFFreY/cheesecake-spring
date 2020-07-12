package org.yeffrey.cheesecakespring.infrastructure.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.library.domain.DomainSamples
import org.yeffrey.cheesecakespring.library.domain.Library
import org.yeffrey.cheesecakespring.library.domain.UserId

class ActivityResourcesRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Autowired
    private TestEntityManager entityManager

    @Autowired
    LibraryRepositoryAdapter libraryRepository

    @Autowired
    ActivityRepositoryAdapter activityRepository

    @Autowired
    ResourceRepositoryAdapter resourceRepository

    Library library

    def setup() {
        library = libraryRepository.save(Library.from(UserId.from(faker.name().username())))
    }


    def "given a saved activity and existing saved resources, resources added to activity link is persisted"() {
        given: "valid input"
            def activity = activityRepository.save(givenActivity(library))
            def resource = resourceRepository.save(givenResource(library))
            def resource2 = resourceRepository.save(givenResource(library))
            def resource3 = resourceRepository.save(givenResource(library))

        when: "resource added to activity"
            def qty = faker.number().randomDigitNotZero()
            activity.addResource(resource, qty)
            activity.addResource(resource2, qty)
            activity.addResource(resource3, qty)
            def result = activityRepository.save(activity)
            flushAndClear()

        then: "activity is saved and resources are associated with it"
            def activityWithResources = activityRepository.findById(result.id)
            activityWithResources.isPresent()
            activityWithResources.get().resources.size() == 3
            //TODO check quantity and resource ids
    }


    def "given a saved activity and associated resources, activity resources can be retrieved"() {
        given: "valid input"
            def activity = activityRepository.save(givenActivity(library))
            def resource = resourceRepository.save(givenResource(library))
            def resource2 = resourceRepository.save(givenResource(library))
            def resource3 = resourceRepository.save(givenResource(library))

        when: "resource added to activity"
            def qty = faker.number().randomDigitNotZero()
            activity.addResource(resource, qty)
            activity.addResource(resource2, qty)
            activity.addResource(resource3, qty)
            activityRepository.save(activity)
            flushAndClear()

        then: "activity is saved and resources are associated with it"
            def activityWithResources = activityRepository.findActivityResources(activity.id)
            activityWithResources.size() == 3
    }

    def "given a saved activity and associated resources, resources can be removed from the activity"() {
        given: "valid input"
            def activity = activityRepository.save(givenActivity(library))
            def resource = resourceRepository.save(givenResource(library))
            def resource2 = resourceRepository.save(givenResource(library))
            def resource3 = resourceRepository.save(givenResource(library))

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
            def activityWithResources = activityRepository.findActivityResources(activity.id)

        then: "resources are removed"
            activityWithResources.size() == 1
    }
}
