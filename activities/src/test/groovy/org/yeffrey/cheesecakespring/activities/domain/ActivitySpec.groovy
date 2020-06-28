package org.yeffrey.cheesecakespring.activities.domain

import org.yeffrey.cheesecakespring.activities.BaseSpecification
import spock.lang.Unroll

class ActivitySpec extends BaseSpecification implements DomainSamples {

    def "an activity may require resource material"() {
        given: "an activity and a resource"
            def aUser = UserId.from(faker.name().username())
            def resource = givenResource(aUser)
            def activity = givenActivity(aUser)
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

    def "an activity must not add resource already added to activity"() {
        given: "an activity and a resource"
            def aUser = UserId.from(faker.name().username())
            def resource = givenResource(aUser)
            def activity = givenActivity(aUser)
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

    @Unroll
    def "should refuse to create a new activity because #problem"() {
        when: "creating an activity"
            Activity.from(name, null, username)

        then: "fails"
            thrown NullPointerException

        where: "input is invalid"
            problem            | name                                        | username
            "name is null"     | null                                        | UserId.from(faker.name().username())
            "username is null" | ActivityName.from(faker.lorem().sentence()) | null
    }

    def "should refuse to update an activity name with null"() {
        given: "an existing activity"
            def activity = givenActivity()
        when: "update an activity"
            activity.updateDetails(null, ActivityDescription.from(faker.lorem().paragraph()))

        then: "fails"
            thrown NullPointerException
    }

    def "update an activity description with null is valid"() {
        given: "an existing activity"
            def activity = givenActivity()
        when: "update an activity"
            activity.updateDetails(ActivityName.from(faker.lorem().sentence()), null)

        then: "fails"
            activity.description == null
    }
}
