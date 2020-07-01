package org.yeffrey.cheesecakespring.infrastructure

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.activities.domain.DomainSamples
import org.yeffrey.cheesecakespring.activities.domain.UserId
import org.yeffrey.cheesecakespring.activities.ports.ActivityRepository
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository
import spock.lang.Shared

import static org.hamcrest.Matchers.hasSize

class ActivityRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Shared
    def faker = new Faker()

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ActivityRepository activityRepository

    @Autowired
    ResourceRepository resourceRepository

    def "should save an activity"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def activity = givenActivity(user)

        expect: "activity is saved and an id is assigned"
            def result = activityRepository.save(activity)
            result.id != null
            result.description == activity.description
            result.name == activity.name
            result.belongsTo(user)
    }


    def "should retrieve an activity entity created by the same user"() {
        given: "an activity saved by a user"
            def aUser = UserId.from(faker.name().username())
            def activityOfUser = activityRepository.save(givenActivity(aUser))
            flushAndClear()

        expect: "the activity entity is retrieved for the same user"
            def result = activityRepository.findByIdAndOwnerId(activityOfUser.id, aUser)
            result.isPresent()
            result.get().id == activityOfUser.id
            result.get().name == activityOfUser.name
            result.get().description == activityOfUser.description
    }

    def "should retrieve an activity details created by the same user"() {
        given: "an activity saved by a user"
            def aUser = UserId.from(faker.name().username())
            def activityOfUser = activityRepository.save(givenActivity(aUser))
            flushAndClear()

        expect: "the activity details is retrieved for the same user"
            def result = activityRepository.findDetailsByIdAndOwnerId(activityOfUser.id, aUser)
            result.isPresent()
            result.get().id == activityOfUser.id
            result.get().name == activityOfUser.name.asString()
            result.get().description == activityOfUser.description.asString()
    }

    def "should not retrieve an activity created by another user"() {
        given: "an activity saved by a user"
            def aUser = UserId.from(faker.name().username())
            def activityOfUser = activityRepository.save(givenActivity(aUser))

        when: "another user tries to retrieve the activity"
            def result = activityRepository.findByIdAndOwnerId(activityOfUser.id, UserId.from("anotherUser"))

        then: "no result"
            result.isEmpty()
    }

    def "should retrieve all activities of the given user only"() {
        given: "some activities which belong to user"
            def aUser = UserId.from("user")
            def activity1OfUser = activityRepository.save(givenActivity(aUser))
            def activity2OfUser = activityRepository.save(givenActivity(aUser))
            def activity3OfUser = activityRepository.save(givenActivity(aUser))

        and: "some activities which belong to another user"
            def anotherUser = UserId.from("anotherUser")
            def activity1OfAnotherUser = activityRepository.save(givenActivity(anotherUser))

        expect: "all activities which belong to the user are retrieved"
            def activitiesOfUser = activityRepository.findAllByOwnerId(aUser)
            activitiesOfUser hasSize(3)
            activitiesOfUser*.id == [activity1OfUser.id, activity2OfUser.id, activity3OfUser.id]
            activitiesOfUser*.name == [activity1OfUser.name.asString(), activity2OfUser.name.asString(), activity3OfUser.name.asString()]
            activitiesOfUser*.id.every { it != activity1OfAnotherUser.id }
    }

    def "should inform the activity can be found for the given user id"() {
        given: "some activities which belong to user"
            def aUser = UserId.from("user")
            def activityOfUser = activityRepository.save(givenActivity(aUser))
            def anotherUser = UserId.from("anotherUser")
            def activityOfAnotherUser = activityRepository.save(givenActivity(anotherUser))
        when: "activities existences is checked for user"
            def userActivityExistsForUser = activityRepository.existsByIdAndOwnerId(activityOfUser.id, aUser)
            def anotherUserActivityExistsForUser = activityRepository.existsByIdAndOwnerId(activityOfAnotherUser.id, aUser)
        then: "activity which belongs to user is found, the one which belongs to another user is not"
            userActivityExistsForUser
            !anotherUserActivityExistsForUser

    }
}
