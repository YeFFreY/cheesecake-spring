package org.yeffrey.cheesecakespring.activities

import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails

import static org.hamcrest.Matchers.hasSize

class ResourceStoriesSpec extends BaseSpecification {

    def newRegisterCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    }
    def updateCommand = new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    def stories = new ActivitiesConfiguration().resourceStories(authenticatedService)

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

    def "should not show a resource details which belongs to another user"() {
        given: "two authenticated users"
            authenticatedService.getAuthenticatedUserId() >>> [aUser, anotherUser]

        and: "a resource is registered in the system by a user"
            def registerCommand = newRegisterCommand()
            def id = stories.registerResource(registerCommand)

        when: "another user tries to retrieve the resource of a user"
            def result = stories.findById(id)

        then: "system return nothing"
            result == Optional.empty()
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

    def "should not update resource of another user"() {
        given: "an authenticated user, then another one, followed by the original one"
            authenticatedService.getAuthenticatedUserId() >> aUser >> anotherUser >> aUser
        and: "a resource registered in the system by this user"
            def registerCommand = newRegisterCommand()
            def id = stories.registerResource(registerCommand)

        when: "another user updates this resource"
            stories.updateResource(id, updateCommand)

        then: "user sees the resource without the update"
            def updated = stories.findById(id)
            updated.isPresent()
            updated.get().id == id
            updated.get().name == registerCommand.name
            updated.get().description == registerCommand.description
            updated.get().quantityUnit.name() == registerCommand.quantityUnit

    }

    def "should retrieve resources overview belonging to the user"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser
        and: "resources registered in the system by this user"
            def aResource = newRegisterCommand()
            def anotherResource = newRegisterCommand()
            def anId = stories.registerResource(aResource)
            def anotherId = stories.registerResource(anotherResource)

        expect: "the user retrieves his resources"
            def resources = stories.list()
            resources hasSize(2)
            resources.collect { it.id } == [anId, anotherId]
            resources.collect { it.name } == [aResource.name, anotherResource.name]
    }
}
