package org.yeffrey.cheesecakespring.infrastructure.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.common.domain.UserId
import org.yeffrey.cheesecakespring.library.domain.DomainSamples
import org.yeffrey.cheesecakespring.library.domain.Library

import static org.hamcrest.Matchers.hasSize

class ActivityRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Autowired
    private TestEntityManager entityManager

    @Autowired
    LibraryRepositoryAdapter libraryRepository

    @Autowired
    ActivityRepositoryAdapter activityRepository

    Library library

    def setup() {
        library = libraryRepository.save(Library.from(UserId.from(faker.name().username())))
    }


    def "should save an activity"() {
        given: "valid input"
            def activity = givenActivity(library)

        expect: "activity is saved and an id is assigned"
            def result = activityRepository.save(activity)
            flushAndClear()
            result.id != null
            result.description == activity.description
            result.name == activity.name
    }


    def "should retrieve an activity entity"() {
        given: "an activity saved"
            def activity = activityRepository.save(givenActivity(library))
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
            def activity = activityRepository.save(givenActivity(library))
            flushAndClear()

        expect: "the activity details is retrieved"
            def result = activityRepository.findDetailsById(activity.id)
            result.isPresent()
            result.get().id == activity.id
            result.get().name == activity.name.asString()
            result.get().description == activity.description.asString()
    }

    def "should retrieve all activities from library"() {
        given: "some activities"
            def activity = activityRepository.save(givenActivity(library))
            def activity2 = activityRepository.save(givenActivity(library))
            def activity3 = activityRepository.save(givenActivity(library))

        expect: "all activities are retrieved"
            def activitiesOfUser = activityRepository.findActivitiesByLibrary(library)
            activitiesOfUser hasSize(3)
            activitiesOfUser*.id == [activity.id, activity2.id, activity3.id]
            activitiesOfUser*.name == [activity.name.asString(), activity2.name.asString(), activity3.name.asString()]
    }

    def "should tell that the activity belongs to library"() {
        given: "some activities"
            def activity = activityRepository.save(givenActivity(library))
            flushAndClear()

        expect: "activity belongs to library"
            def belongs = activityRepository.activityBelongsToUserLibrary(activity.id, library.ownerId)
            belongs
    }

    def "should tell that the activity doesnt belong to library"() {
        given: "some activities"
            def anotherLibrary = libraryRepository.save(Library.from(UserId.from("anotherUser")))
            def activity = activityRepository.save(givenActivity(anotherLibrary))
            flushAndClear()

        expect: "activity doesnt belong to library"
            def belongs = activityRepository.activityBelongsToUserLibrary(activity.id, library.ownerId)
            !belongs
    }

}
