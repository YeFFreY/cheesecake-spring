package org.yeffrey.cheesecakespring.activities

import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand

class ActivityStoriesSpec extends BaseSpecification {

    def newRegisterCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    }
    def updateCommand = new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    def stories = new ActivitiesConfiguration().activityStories()

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

}
