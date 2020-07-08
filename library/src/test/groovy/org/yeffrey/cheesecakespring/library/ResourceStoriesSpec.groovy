package org.yeffrey.cheesecakespring.library

import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails

class ResourceStoriesSpec extends BaseSpecification {

    def newRegisterCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    }
    def updateCommand = new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    def stories = new ActivitiesConfiguration().resourceStories()

    def "should show the resource details for the user who created it"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        and: "a resource is registered in the system by this user"
            def registerCommand = newRegisterCommand()
            def id = stories.registerResource(registerCommand)

        expect: "system will return the resource for this user"
            Optional<ResourceDetails> details = stories.findById(id)
            details.isPresent()
            details.get().id == id
            details.get().name == registerCommand.name
            details.get().description == registerCommand.description
            details.get().quantityUnit.name() == registerCommand.quantityUnit
    }

    def "should return empty when a user try to retrieve a resource unknown by the system"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        expect: "system will return nothing"
            Optional<ResourceDetails> details = stories.findById(faker.random().nextLong())
            details.isEmpty()
    }

    def "should show an updated resource after user updates his resource"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser
        and: "a resource registered in the system by this user"
            def id = stories.registerResource(newRegisterCommand())

        when: "user updates this resource"
            stories.updateResource(id, updateCommand)

        then: "user sees the last update he made"
            def updated = stories.findById(id)
            updated.isPresent()
            updated.get().id == id
            updated.get().name == updateCommand.name
            updated.get().description == updateCommand.description
            updated.get().quantityUnit.name() == updateCommand.quantityUnit

    }

}
