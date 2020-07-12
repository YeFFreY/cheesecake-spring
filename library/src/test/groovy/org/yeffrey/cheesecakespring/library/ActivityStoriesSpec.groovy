package org.yeffrey.cheesecakespring.library

import org.yeffrey.cheesecakespring.library.core.ResourceNotFoundException
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand
import org.yeffrey.cheesecakespring.library.stories.ActivityStories
import org.yeffrey.cheesecakespring.library.stories.LibraryStories

class ActivityStoriesSpec extends BaseSpecification {

    def newRegisterCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    }
    def updateCommand = new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    def libraryStories = new LibraryStories(authenticatedService, libraryRepository)
    def stories = new ActivityStories(libraryStories, activityRepository)

    def setup() {
        authenticatedService.findAuthenticatedUserId() >> aUser
        libraryStories.createLibraryForCurrentUser()
    }

    def "should show the activity details for the user who created it"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        and: "an activity is registered in the system by this user"
            def registerCommand = newRegisterCommand()
            def id = stories.addToLibrary(registerCommand)

        expect: "system will return the activity for this user"
            ActivityDetails details = stories.findDetails(id)
            details.id == id
            details.name == registerCommand.name
            details.description == registerCommand.description
    }

    def "should return empty when a user try to retrieve an activity unknown by the system"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        when: "retrieving an activity with an unknown id"
            ActivityDetails details = stories.findDetails(faker.random().nextLong())
        then: "system will return nothing"
            thrown(ResourceNotFoundException)
            details == null
    }


    def "should show an updated activity after user updates his activity"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser
        and: "an activity registered in the system by this user"
            def id = stories.addToLibrary(newRegisterCommand())
            println id

        when: "user updates this activity"
            stories.updateInformation(id, updateCommand)

        then: "user sees the last update he made"
            def updated = stories.findDetails(id)
            println id
            println updated
            updated.id == id
            updated.name == updateCommand.name
            updated.description == updateCommand.description

    }

}
