package org.yeffrey.cheesecakespring.activities

import org.yeffrey.cheesecakespring.activities.domain.*
import spock.lang.Unroll

class ActivitySpec extends BaseSpecification {

    def "an activity may require resource material"() {
        given: "an activity and a resource"
            def aUser = UserId.from(faker.name().username())
            def resource = Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser)
            def activity = Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), aUser)
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
            def activity = Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), UserId.from(faker.name().username()))
        when: "update an activity"
            activity.updateDetails(null, ActivityDescription.from(faker.lorem().paragraph()))

        then: "fails"
            thrown NullPointerException
    }

    def "update an activity description with null is valid"() {
        given: "an existing activity"
            def activity = Activity.from(ActivityName.from(faker.lorem().sentence()), ActivityDescription.from(faker.lorem().paragraph()), UserId.from(faker.name().username()))
        when: "update an activity"
            activity.updateDetails(ActivityName.from(faker.lorem().sentence()), null)

        then: "fails"
            activity.description == null
    }
}
