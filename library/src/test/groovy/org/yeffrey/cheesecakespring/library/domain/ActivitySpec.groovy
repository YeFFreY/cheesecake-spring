package org.yeffrey.cheesecakespring.library.domain

import org.yeffrey.cheesecakespring.library.BaseSpecification
import spock.lang.Unroll

class ActivitySpec extends BaseSpecification implements DomainSamples {
    def library = Library.from(UserId.from(faker.name().username()))

    @Unroll
    def "should refuse to create a new activity because #problem"() {
        when: "creating an activity"
            Activity.from(library, name, null)

        then: "fails"
            thrown NullPointerException

        where: "input is invalid"
            problem        | name
            "name is null" | null
    }

    def "should refuse to update an activity name with null"() {
        given: "an existing activity"
            def activity = givenActivity(library)
        when: "update an activity"
            activity.updateDetails(null, ActivityDescription.from(faker.lorem().paragraph()))

        then: "fails"
            thrown NullPointerException
    }

    def "update an activity description with null is valid"() {
        given: "an existing activity"
            def activity = givenActivity(library)
        when: "update an activity"
            activity.updateDetails(ActivityName.from(faker.lorem().sentence()), null)

        then: "fails"
            activity.description == null
    }

    /***********************************************************************
     Activity Resources
     ***********************************************************************/

    def "an activity may require resource material"() {
        given: "an activity and a resource"
            def resource = givenResource(library)
            def activity = givenActivity(library)
            def qty = faker.number().randomDigitNotZero()
        when: "a Resource is added to activity"
            def added = activity.addResource(resource, qty)
        then: "Activity has one resource"
            added
            activity.getResources().size() == 1
            activity.getResources()[0].activity == activity
            activity.getResources()[0].resource == resource
            activity.getResources()[0].quantity == qty
    }

    def "an activity refuse resource material with zero or less quantity"() {
        given: "an activity and a resource"
            def resource = givenResource(library)
            def activity = givenActivity(library)
        when: "a Resource with zero quantity is added to activity"
            activity.addResource(resource, quantity)
        then: "resource is refused"
            thrown(IllegalArgumentException)
            activity.getResources().size() == 0
        where: "input is invalid"
            problem                | quantity
            "quantity is zero"     | 0
            "quantity is negative" | faker.number().numberBetween(Integer.MIN_VALUE, -1)

    }

    def "a resource may be removed from activity"() {
        given: "an activity which has two resource"
            def resource = givenResource(library)
            def otherResource = givenResource(library)
            def activity = givenActivity(library)
            activity.addResource(resource, faker.number().randomDigitNotZero())
            activity.addResource(otherResource, faker.number().randomDigitNotZero())
            activity.resources.size() == 2
        when: "resource is removed from activity"
            def removed = activity.removeResource(resource)
        then: "activity has one resource"
            removed
            activity.resources.size() == 1
            activity.resources[0].resource == otherResource
    }


    def "a resource not associated with an activity cannot be not removed from activity"() {
        given: "an activity which has two resource"
            def resource = givenResource(library)
            def otherResource = givenResource(library)
            def activity = givenActivity(library)
            activity.addResource(resource, faker.number().randomDigitNotZero())
            activity.resources.size() == 1
        when: "resource is removed from activity"
            def removed = activity.removeResource(otherResource)
        then: "activity has one resource"
            !removed
            activity.resources.size() == 1
            activity.resources[0].resource == resource
    }

    def "an activity must not add resource already added to activity"() {
        given: "an activity and a resource"
            def resource = givenResource(library)
            def activity = givenActivity(library)
        and: "this activity contains this resource"
            def qty = faker.number().randomDigitNotZero()
            activity.addResource(resource, qty)
        when: "the same resource is added to activity"
            def added = activity.addResource(resource, faker.number().randomDigitNotZero())
        then: "resource is not present twice and original qty is preserved"
            !added
            activity.getResources().size() == 1
            activity.getResources()[0].resource == resource
            activity.getResources()[0].quantity == qty
    }

    def "user can update an activity resource quantity"() {
        given: "an activity and a resource"
            def resource = givenResource(library)
            def anotherResource = givenResource(library)
            def activity = givenActivity(library)
            def initialQty = faker.number().randomDigitNotZero()
            activity.addResource(resource, initialQty)
            activity.addResource(anotherResource, initialQty)
        when: "quantity of resource is updated"
            def newQuantity = faker.number().randomDigitNotZero()
            activity.updateResource(resource, newQuantity)
        then: "Activity has updated quantity for the resource"
            activity.resources.stream().anyMatch({ ar -> ar.quantity == newQuantity && ar.resource == resource })
            activity.resources.stream().anyMatch({ ar -> ar.quantity == initialQty && ar.resource == anotherResource })
    }

    def "user cannot update an activity resource quantity with zero or negative value"() {
        given: "an activity and a resource"
            def activity = givenActivity(library)
            def resource = givenResource(library)
            def initialQty = faker.number().numberBetween(1, 150)
            activity.addResource(resource, initialQty)

        when: "quantity of resource is updated"
            activity.updateResource(resource, newQuantity)

        then: "Activity resource quantity is not updated"
            thrown(IllegalArgumentException)
            activity.resources.size() == 1
            activity.resources.stream().anyMatch({ ar -> ar.quantity == initialQty && ar.resource == resource })

        where: "input is invalid"
            problem                | newQuantity
            "quantity is zero"     | 0
            "quantity is negative" | faker.number().numberBetween(Integer.MIN_VALUE, -1)

    }

    def "update a quantity of a resource not present in activity has no impact"() {
        given: "an activity and a resource"
            def resource = givenResource(library)
            def anotherResource = givenResource(library)
            def resourceNotInActivity = givenResource(library)
            def activity = givenActivity(library)
            def initialQty = faker.number().randomDigitNotZero()
            activity.addResource(resource, initialQty)
            activity.addResource(anotherResource, initialQty)
        when: "quantity of resource not in activity is updated"
            def newQuantity = faker.number().randomDigitNotZero()
            activity.updateResource(resourceNotInActivity, newQuantity)
        then: "Activity resources have not been impacted"
            activity.resources.size() == 2
            activity.resources.stream().anyMatch({ ar -> ar.quantity == initialQty && ar.resource == resource })
            activity.resources.stream().anyMatch({ ar -> ar.quantity == initialQty && ar.resource == anotherResource })
    }

}
