package org.yeffrey.cheesecakespring.infrastructure.persistence


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.library.domain.DomainSamples
import org.yeffrey.cheesecakespring.library.ports.ActivityRepository
import org.yeffrey.cheesecakespring.library.ports.ResourceRepository

import static org.hamcrest.Matchers.hasSize

class ActivityRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ActivityRepository activityRepository

    @Autowired
    ResourceRepository resourceRepository

    def "should save an activity"() {
        given: "valid input"
            def activity = givenActivity()

        expect: "activity is saved and an id is assigned"
            def result = activityRepository.save(activity)
            result.id != null
            result.description == activity.description
            result.name == activity.name
    }


    def "should retrieve an activity entity"() {
        given: "an activity saved"
            def activity = activityRepository.save(givenActivity())
            flushAndClear()

        expect: "the activity entity is retrieved"
            def result = activityRepository.findById(activity.id)
            result.isPresent()
            result.get().id == activity.id
            result.get().name == activity.name
            result.get().description == activity.description
    }

    def "should retrieve an activity details"() {
        given: "an activity saved"
            def activity = activityRepository.save(givenActivity())
            flushAndClear()

        expect: "the activity details is retrieved"
            def result = activityRepository.findDetailsById(activity.id)
            result.isPresent()
            result.get().id == activity.id
            result.get().name == activity.name.asString()
            result.get().description == activity.description.asString()
    }

    def "should retrieve all activities"() {
        given: "some activities"
            def activity = activityRepository.save(givenActivity())
            def activity2 = activityRepository.save(givenActivity())
            def activity3 = activityRepository.save(givenActivity())

        expect: "all activities are retrieved"
            def activitiesOfUser = activityRepository.findAll()
            activitiesOfUser hasSize(3)
            activitiesOfUser*.id == [activity.id, activity2.id, activity3.id]
            activitiesOfUser*.name == [activity.name.asString(), activity2.name.asString(), activity3.name.asString()]
    }

}
