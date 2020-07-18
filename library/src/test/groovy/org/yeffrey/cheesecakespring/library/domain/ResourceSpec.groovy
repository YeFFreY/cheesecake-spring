package org.yeffrey.cheesecakespring.library.domain

import org.yeffrey.cheesecakespring.common.domain.UserId
import org.yeffrey.cheesecakespring.library.BaseSpecification
import spock.lang.Unroll

class ResourceSpec extends BaseSpecification implements LibrarySamples {
    def library = Library.from(UserId.from(faker.name().username()))

    def "a resource holds the details it is given"() {
        given: "some input"
            def name = ResourceName.from(faker.lorem().sentence())
            def description = ResourceDescription.from(faker.lorem().paragraph())
            def qtyUnit = ResourceQuantityUnit.Item
        expect: "a resource has a quantity unit"
            def resource = Resource.from(library, name, description, qtyUnit)
            resource.name == name
            resource.description == description
            resource.quantityUnit == qtyUnit
    }

    @Unroll
    def "should refuse to create a new resource because #problem"() {
        when: "creating an resource"
            Resource.from(library, name, null, qtyUnit)

        then: "fails"
            thrown NullPointerException

        where: "input is invalid"
            problem           | name                                        | qtyUnit
            "name is null"    | null                                        | ResourceQuantityUnit.Item
            "qtyUnit is null" | ResourceName.from(faker.lorem().sentence()) | null
    }

    @Unroll
    def "should refuse to update an existing resource because #problem"() {
        given: "an existing resource"
            def resource = givenResource(library)
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
            def resource = givenResource(library)
        when: "update an activity"
            resource.updateDetails(ResourceName.from(faker.lorem().sentence()), null, ResourceQuantityUnit.Item)

        then: "fails"
            resource.description == null
    }
}
