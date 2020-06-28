package org.yeffrey.cheesecakespring.activities.domain

import org.yeffrey.cheesecakespring.activities.BaseSpecification
import spock.lang.Unroll

class ResourceSpec extends BaseSpecification implements DomainSamples {

    def "a resource holds the details it is given"() {
        given: "some input"
            def name = ResourceName.from(faker.lorem().sentence())
            def description = ResourceDescription.from(faker.lorem().paragraph())
            def username = UserId.from(faker.name().username())
            def qtyUnit = ResourceQuantityUnit.Item
        expect: "a resource has a quantity unit"
            def resource = Resource.from(name, description, qtyUnit, username)
            resource.name == name
            resource.description == description
            resource.belongsTo(username)
            resource.quantityUnit == qtyUnit
    }

    @Unroll
    def "should refuse to create a new resource because #problem"() {
        when: "creating an resource"
            Resource.from(name, null, qtyUnit, username)

        then: "fails"
            thrown NullPointerException

        where: "input is invalid"
            problem            | name                                        | username                             | qtyUnit
            "name is null"     | null                                        | UserId.from(faker.name().username()) | ResourceQuantityUnit.Item
            "qtyUnit is null"  | ResourceName.from(faker.lorem().sentence()) | UserId.from(faker.name().username()) | null
            "username is null" | ResourceName.from(faker.lorem().sentence()) | null                                 | ResourceQuantityUnit.Item
    }

    @Unroll
    def "should refuse to update an existing resource because #problem"() {
        given: "an existing resource"
            def resource = givenResource()
        when: "creating an resource"
            resource.updateDetails(name, null, qtyUnit)

        then: "fails"
            thrown NullPointerException

        where: "input is invalid"
            problem           | name                                        | qtyUnit
            "name is null"    | null                                        | ResourceQuantityUnit.Item
            "qtyUnit is null" | ResourceName.from(faker.lorem().sentence()) | null
    }

    def "update a resource description with null is valid"() {
        given: "an existing activity"
            def resource = givenResource()
        when: "update an activity"
            resource.updateDetails(ResourceName.from(faker.lorem().sentence()), null, ResourceQuantityUnit.Item)

        then: "fails"
            resource.description == null
    }
}
