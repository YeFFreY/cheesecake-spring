package org.yeffrey.cheesecakespring.library

import org.yeffrey.cheesecakespring.library.dto.AddResourceToActivityCommand
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand
import org.yeffrey.cheesecakespring.library.stories.ActivityResourceStories
import org.yeffrey.cheesecakespring.library.stories.ActivityStories
import org.yeffrey.cheesecakespring.library.stories.LibraryStories
import org.yeffrey.cheesecakespring.library.stories.ResourceStories

class ActivityResourceStoriesSpec extends BaseSpecification {
    def libraryStories = new LibraryStories(authenticatedService, libraryRepository)
    def resourceStories = new ResourceStories(libraryStories, resourceRepository)
    def activityStories = new ActivityStories(libraryStories, activityRepository)
    def activityResourceStories = new ActivityResourceStories(activityRepository, resourceRepository)

    /* FIXME move this in super class to reuse them in every specs !! */

    def newActivityCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph())
    }

    def newResourceCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item")
    }

    def newActivityResourceCommand(Long resourceId) {
        return new AddResourceToActivityCommand(resourceId, faker.number().randomDigitNotZero())
    }

    def setup() {
        authenticatedService.findAuthenticatedUserId() >> aUser
        libraryStories.createLibraryForCurrentUser()
    }

    def "An resource can be added an activity"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        and: "an activity and a resource created by a user"
            def activityId = activityStories.addToLibrary(newActivityCommand())
            def resourceId = resourceStories.addToLibrary(newResourceCommand())

        when: "the resource is added to the activity"
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.addResourceToActivity(activityId, cmd)

        then: "activity has the resource linked to it"
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 1
            activityResources.get(0).quantity == cmd.quantity
            activityResources.get(0).id == cmd.resourceId

    }

    def "An activity doesn't accept duplicate resource"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        and: "an activity with a resource"
            def activityId = activityStories.addToLibrary(newActivityCommand())
            def resourceId = resourceStories.addToLibrary(newResourceCommand())
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.addResourceToActivity(activityId, cmd)

        when: "the resource is added twice to the activity"
            def added = activityResourceStories.addResourceToActivity(activityId, newActivityResourceCommand(resourceId))

        then: "activity has only a single resource with unchanged qties"
            !added
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 1
            activityResources.get(0).quantity == cmd.quantity
            activityResources.get(0).id == cmd.resourceId

    }

    def "A resource can be removed from an activity"() {
        given: "an authenticated user"
            authenticatedService.findAuthenticatedUserId() >> aUser

        and: "an activity with a resource"
            def activityId = activityStories.addToLibrary(newActivityCommand())
            def resourceId = resourceStories.addToLibrary(newResourceCommand())
            activityResourceStories.addResourceToActivity(activityId, newActivityResourceCommand(resourceId))

        when: "the resource is removed from the activity"
            def removed = activityResourceStories.removeResourceFromActivity(activityId, resourceId)

        then: "activity has no more the resources"
            removed
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 0
    }

}
