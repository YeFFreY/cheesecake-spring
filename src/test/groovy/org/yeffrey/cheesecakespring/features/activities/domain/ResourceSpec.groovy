package org.yeffrey.cheesecakespring.features.activities.domain


import org.yeffrey.cheesecakespring.features.BaseSpecification

class ResourceSpec extends BaseSpecification {

    def "a resource holds the details it is given"() {
        given: "some input"
            def name = faker.lorem().sentence()
            def description = faker.lorem().paragraph()
            def username = faker.name().username()
            def qtyUnit = ResourceQuantityUnit.Item
        expect: "a resource has a quantity unit"
            def resource = Resource.from(name, description, qtyUnit, username)
            resource.name == name
            resource.description == description
            resource.belongsTo(username)
            resource.quantityUnit == qtyUnit
    }
}
