package org.yeffrey.cheesecakespring.activities

import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand

import static org.hamcrest.Matchers.hasSize

class ActivityStoriesSpec extends BaseSpecification {

    def newRegisterCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    }
    def updateCommand = new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    def stories = new ActivitiesConfiguration().activityStories(authenticatedService)

    def "should show the activity details for the user who created it"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        and: "an activity is registered in the system by this user"
            def registerCommand = newRegisterCommand()
            def id = stories.registerActivity(registerCommand)

        expect: "system will return the activity for this user"
            Optional<ActivityDetails> details = stories.findById(id)
            details.isPresent()
            details.get().id == id
            details.get().name == registerCommand.name
            details.get().description == registerCommand.description
    }

    def "should return empty when a user try to retrieve an activity unknown by the system"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        expect: "system will return nothing"
            Optional<ActivityDetails> details = stories.findById(faker.random().nextLong())
            details.isEmpty()
    }

    def "should not show an activity details which belongs to another user"() {
        given: "two authenticated users"
            authenticatedService.getAuthenticatedUserId() >>> [aUser, anotherUser]

        and: "an activity is registered in the system by a user"
            def registerCommand = newRegisterCommand()
            def id = stories.registerActivity(registerCommand)

        when: "another user tries to retrieve the activity of a user"
            def result = stories.findById(id)

        then: "system return nothing"
            result == Optional.empty()
    }

    def "should show an updated activity after user updates his activity"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser
        and: "an activity registered in the system by this user"
            def id = stories.registerActivity(newRegisterCommand())

        when: "user updates this activity"
            stories.updateActivity(id, updateCommand)

        then: "user sees the last update he made"
            def updated = stories.findById(id)
            updated.isPresent()
            updated.get().id == id
            updated.get().name == updateCommand.name
            updated.get().description == updateCommand.description

    }

    def "should not update activity of another user"() {
        given: "an authenticated user, then another one, followed by the original one"
            authenticatedService.getAuthenticatedUserId() >> aUser >> anotherUser >> aUser
        and: "an activity registered in the system by this user"
            def registerCommand = newRegisterCommand()
            def id = stories.registerActivity(registerCommand)

        when: "another user updates this activity"
            stories.updateActivity(id, updateCommand)

        then: "user sees the activity without the update"
            def updated = stories.findById(id)
            updated.isPresent()
            updated.get().id == id
            updated.get().name == registerCommand.name
            updated.get().description == registerCommand.description

    }

    def "should retrieve activities overview belonging to the user"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser
        and: "activities registered in the system by this user"
            def anActivity = newRegisterCommand()
            def anotherActivity = newRegisterCommand()
            def anId = stories.registerActivity(anActivity)
            def anotherId = stories.registerActivity(anotherActivity)

        expect: "the user retrieves his activities"
            def activities = stories.list()
            activities hasSize(2)
            activities.collect { it.id } == [anId, anotherId]
            activities.collect { it.name } == [anActivity.name, anotherActivity.name]
    }
}
