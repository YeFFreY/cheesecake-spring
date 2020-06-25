package org.yeffrey.cheesecakespring.infrastructure

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.activities.*
import org.yeffrey.cheesecakespring.activities.domain.*
import spock.lang.Shared
import spock.lang.Unroll

import static org.hamcrest.Matchers.hasSize

class ActivityRepositorySpec extends IntegrationSpecification {
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
            def name = ActivityName.from(faker.lorem().sentence())
            def description = ActivityDescription.from(faker.lorem().paragraph())
            def username = UserId.from(faker.name().username())

        expect: "activity is saved and an id is assigned"
            def result = activityRepository.save(Activity.from(name, description, username))
            result.id != null
            result.description == description
            result.name == name
            result.belongsTo(username)
    }

    @Unroll
    def "should refuse when saving new activity because #problem"() {
        when: "saving an activity"
            def result = activityRepository.save(Activity.from(name, description, username))
            entityManager.flush()

        then: "fails"
            result == null
            thrown NullPointerException

        where: "input is invalid"
            problem            | name                                        | description                                         | username
            "name is null"     | null                                        | ActivityDescription.from(faker.lorem().paragraph()) | UserId.from(faker.name().username())
            "username is null" | ActivityName.from(faker.lorem().sentence()) | ActivityDescription.from(faker.lorem().paragraph()) | null
    }

    def "should retrieve an activity entity created by the same user"() {
        given: "an activity saved by a user"
            def aUser = UserId.from(faker.name().username())
            def activityOfUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))
            entityManager.flush()

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
            def activityOfUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))
            entityManager.flush()

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
            def activityOfUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))

        when: "another user tries to retrieve the activity"
            def result = activityRepository.findByIdAndOwnerId(activityOfUser.id, UserId.from("anotherUser"))

        then: "no result"
            result.isEmpty()
    }

    def "should retrieve all activities of the given user only"() {
        given: "some activities which belong to user"
            def aUser = UserId.from("user")
            def activity1OfUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))
            def activity2OfUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))
            def activity3OfUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))

        and: "some activities which belong to another user"
            def anotherUser = UserId.from("anotherUser")
            def activity1OfAnotherUser = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), anotherUser))

        expect: "all activities which belong to the user are retrieved"
            def activitiesOfUser = activityRepository.findAllByOwnerId(aUser)
            activitiesOfUser hasSize(3)
            activitiesOfUser*.id == [activity1OfUser.id, activity2OfUser.id, activity3OfUser.id]
            activitiesOfUser*.name == [activity1OfUser.name.asString(), activity2OfUser.name.asString(), activity3OfUser.name.asString()]
            activitiesOfUser*.id.every { it != activity1OfAnotherUser.id }
    }

    def "should add a resource to an activity"() {
        given: "valid input"
            def aUser = UserId.from("user")
            def activity = activityRepository.save(Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser))
            def resource = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))
            def qty = faker.number().randomDigitNotZero()
        when: "resource is added to activity"
            activity.addResource(resource, qty)
            activityRepository.save(activity)
            entityManager.flush()
            entityManager.clear()

        then: "activity with relation to resource is saved"
            def activityWithResource = activityRepository.findByIdAndOwnerId(activity.id, activity.ownerId)
            activityWithResource.isPresent()
            activityWithResource.get().resources.size() == 1
            activityWithResource.get().resources[0].resource.id == resource.id
            activityWithResource.get().resources[0].quantity == qty
    }
}
