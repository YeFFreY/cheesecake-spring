package org.yeffrey.cheesecakespring.infrastructure.persistence


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.activities.domain.DomainSamples
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository

import static org.hamcrest.Matchers.hasSize

class ResourceRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ResourceRepository resourceRepository

    def "should save a resource"() {
        given: "valid input"
            def resource = givenResource()

        expect: "resource is saved and an id is assigned"
            def result = resourceRepository.save(resource)
            result.id != null
            result.description == resource.description
            result.name == resource.name
    }

    def "should retrieve a resource entity"() {
        given: "a resource saved"
            def resource = resourceRepository.save(givenResource())
            entityManager.flush()

        expect: "the resource entity is retrieved"
            def result = resourceRepository.findById(resource.id)
            result.isPresent()
            result.get().id == resource.id
            result.get().name == resource.name
            result.get().description == resource.description
            result.get().quantityUnit == resource.quantityUnit
    }

    def "should retrieve a resource details"() {
        given: "a resource saved"
            def resource = resourceRepository.save(givenResource())
            entityManager.flush()

        expect: "the resource details is retrieved"
            def result = resourceRepository.findDetailsById(resource.id)
            result.isPresent()
            result.get().id == resource.id
            result.get().name == resource.name.asString()
            result.get().description == resource.description.asString()
            result.get().quantityUnit == resource.quantityUnit
    }

    def "should retrieve all resources"() {
        given: "some resources"
            def resource = resourceRepository.save(givenResource())
            def resource2 = resourceRepository.save(givenResource())
            def resource3 = resourceRepository.save(givenResource())

        expect: "all resources are retrieved"
            def resourcesOfUser = resourceRepository.findAll()
            resourcesOfUser hasSize(3)
            resourcesOfUser*.id == [resource.id, resource2.id, resource3.id]
            resourcesOfUser*.name == [resource.name.asString(), resource2.name.asString(), resource3.name.asString()]
    }

}
