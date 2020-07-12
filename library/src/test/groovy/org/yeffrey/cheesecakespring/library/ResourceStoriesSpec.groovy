package org.yeffrey.cheesecakespring.library

import org.yeffrey.cheesecakespring.library.core.ResourceNotFoundException
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails
import org.yeffrey.cheesecakespring.library.stories.LibraryStories
import org.yeffrey.cheesecakespring.library.stories.ResourceStories

class ResourceStoriesSpec extends BaseSpecification {

    def newRegisterCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    }
    def updateCommand = new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    def libraryStories = new LibraryStories(authenticatedService, libraryRepository)
    def stories = new ResourceStories(libraryStories, resourceRepository)

    def setup() {
        authenticatedService.findAuthenticatedUserId() >> aUser
        libraryStories.createLibraryForCurrentUser()
    }

    def "should show the resource details for the user who created it"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        and: "a resource is registered in the system by this user"
            def registerCommand = newRegisterCommand()
            def id = stories.addToLibrary(registerCommand)

        expect: "system will return the resource for this user"
            ResourceDetails details = stories.findDetails(id)
            details.id == id
            details.name == registerCommand.name
            details.description == registerCommand.description
            details.quantityUnit.name() == registerCommand.quantityUnit
    }

    def "should return empty when a user try to retrieve a resource unknown by the system"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        when: "retrieving a resource with an unknown id"
            ResourceDetails details = stories.findDetails(faker.random().nextLong())

        then: "system will return nothing"
            thrown(ResourceNotFoundException)
            details == null
    }

    def "should show an updated resource after user updates his resource"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser
        and: "a resource registered in the system by this user"
            def id = stories.addToLibrary(newRegisterCommand())

        when: "user updates this resource"
            stories.updateInformation(id, updateCommand)

        then: "user sees the last update he made"
            def updated = stories.findDetails(id)
            updated.id == id
            updated.name == updateCommand.name
            updated.description == updateCommand.description
            updated.quantityUnit.name() == updateCommand.quantityUnit

    }

}
