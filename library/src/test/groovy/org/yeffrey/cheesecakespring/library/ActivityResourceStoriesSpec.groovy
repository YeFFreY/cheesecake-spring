package org.yeffrey.cheesecakespring.library


import org.yeffrey.cheesecakespring.library.dto.AddResourceToActivityCommand
import org.yeffrey.cheesecakespring.library.dto.AdjustActivityResourceQuantityCommand
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand

class ActivityResourceStoriesSpec extends BaseSpecification {
    def activityStories = new ActivitiesConfiguration().activityStories()
    def resourceStories = new ActivitiesConfiguration().resourceStories()
    def activityResourceStories = new ActivitiesConfiguration().activityResourceStories()

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

}
