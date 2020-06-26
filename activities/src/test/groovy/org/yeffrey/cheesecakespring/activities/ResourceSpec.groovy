package org.yeffrey.cheesecakespring.activities

import org.yeffrey.cheesecakespring.activities.domain.*

class ResourceSpec extends BaseSpecification {

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
}
