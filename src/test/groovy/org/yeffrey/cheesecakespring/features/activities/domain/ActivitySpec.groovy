package org.yeffrey.cheesecakespring.features.activities.domain


import org.yeffrey.cheesecakespring.features.BaseSpecification
import spock.lang.Unroll

class ActivitySpec extends BaseSpecification {

    def "an activity may require material"() {
        given: "an activity and a resource"
            def resource = Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, faker.name().username())
            def activity = Activity.from(faker.lorem().sentence(), faker.lorem().paragraph(), faker.name().username())
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
            problem            | name                     | username
            "name is null"     | null                     | faker.name().username()
            "username is null" | faker.lorem().sentence() | null
    }

    @Unroll
    def "should refuse to update an activity name with null"() {
        given: "an existing activity"
            def activity = Activity.from(faker.lorem().sentence(), faker.lorem().paragraph(), faker.name().username())
        when: "update an activity"
            activity.updateDetails(null, faker.lorem().paragraph())

        then: "fails"
            thrown NullPointerException

    }
}
