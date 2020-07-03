package org.yeffrey.cheesecakespring.activities

import org.yeffrey.cheesecakespring.activities.core.ResourceNotFoundException
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand
import org.yeffrey.cheesecakespring.activities.dto.AdjustActivityResourceQuantityCommand
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand

class ActivityResourceStoriesSpec extends BaseSpecification {
    def activityStories = new ActivitiesConfiguration().activityStories(authenticatedService)
    def resourceStories = new ActivitiesConfiguration().resourceStories(authenticatedService)
    def activityResourceStories = new ActivitiesConfiguration().activityResourceStories(authenticatedService)

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

    def newActivityResourceQuantityAdjustementCommand(Long resourceId) {
        return new AdjustActivityResourceQuantityCommand(faker.number().randomDigitNotZero())
    }


    def "An resource can be added an activity"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        and: "an activity and a resource created by a user"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())

        when: "the resource is added to the activity"
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.activityRequiresResource(activityId, cmd)

        then: "activity has the resource linked to it"
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 1
            activityResources.get(0).quantity == cmd.quantity
            activityResources.get(0).id == cmd.resourceId

    }

    def "An activity doesn't accept duplicate resource"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        and: "an activity with a resource"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.activityRequiresResource(activityId, cmd)

        when: "the resource is added twice to the activity"
            def added = activityResourceStories.activityRequiresResource(activityId, newActivityResourceCommand(resourceId))

        then: "activity has only a single resource with unchanged qties"
            !added
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 1
            activityResources.get(0).quantity == cmd.quantity
            activityResources.get(0).id == cmd.resourceId

    }

    def "Resources of an activity of another user cannot be retrieved"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >>> [aUser, aUser, aUser, anotherUser]

        and: "an activity with a resource which belongs to a user"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())
            activityResourceStories.activityRequiresResource(activityId, newActivityResourceCommand(resourceId))

        when: "another user tries to retrieve the resources of this activityes"
            def result = activityResourceStories.findActivityResources(activityId)


        then: "another user receives no result and activity is not found"
            result == null
            thrown(ResourceNotFoundException)

    }

    def "A resource can be removed from an activity"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        and: "an activity with a resource"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.activityRequiresResource(activityId, cmd)

        when: "the resource is removed from the activity"
            def removed = activityResourceStories.resourceNotRequiredAnymore(activityId, resourceId)

        then: "activity has no more the resources"
            removed
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 0
    }

    def "A resource cannot be removed from an activity by another user"() {
        given: "an authenticated user and another user"
            authenticatedService.getAuthenticatedUserId() >>> [aUser, aUser, aUser, anotherUser, aUser]

        and: "an activity with a resource"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.activityRequiresResource(activityId, cmd)

        when: "the resource is removed from the activity by another user"
            def removed = activityResourceStories.resourceNotRequiredAnymore(activityId, resourceId)

        then: "activity is not impacted and resource is still present"
            thrown(ResourceNotFoundException) // because another user cannot see things of user
            !removed
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources.size() == 1
            activityResources[0].quantity == cmd.quantity
            activityResources[0].id == cmd.resourceId
    }

    def "An activity resource can get its quantity updated"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >> aUser

        and: "an activity with a resource"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())
            activityResourceStories.activityRequiresResource(activityId, newActivityResourceCommand(resourceId))

        when: "a user updates the quantity"
            def cmd = newActivityResourceQuantityAdjustementCommand()
            def updated = activityResourceStories.adjustActivityResourceQuantity(activityId, resourceId, cmd)

        then: "activity resource has its quantity updated"
            updated
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources[0].quantity == cmd.quantity
    }

    def "An activity resource cannot get its quantity updated by another user"() {
        given: "an authenticated user"
            authenticatedService.getAuthenticatedUserId() >>> [aUser, aUser, aUser, anotherUser, aUser]

        and: "an activity with a resource"
            def activityId = activityStories.registerActivity(newActivityCommand())
            def resourceId = resourceStories.registerResource(newResourceCommand())
            def cmd = newActivityResourceCommand(resourceId)
            activityResourceStories.activityRequiresResource(activityId, cmd)

        when: "another user updates the quantity"
            def updated = activityResourceStories.adjustActivityResourceQuantity(activityId, resourceId, newActivityResourceQuantityAdjustementCommand())

        then: "activity resource has its initial quantity"
            thrown(ResourceNotFoundException)
            !updated
            def activityResources = activityResourceStories.findActivityResources(activityId)
            activityResources[0].quantity == cmd.quantity

    }
}
