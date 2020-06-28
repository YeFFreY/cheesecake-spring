package org.yeffrey.cheesecakespring.infrastructure

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.activities.domain.DomainSamples
import org.yeffrey.cheesecakespring.activities.domain.UserId
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository
import spock.lang.Shared

import static org.hamcrest.Matchers.hasSize

class ResourceRepositorySpec extends IntegrationSpecification implements DomainSamples {
    @Shared
    def faker = new Faker()

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ResourceRepository resourceRepository

    def "should save a resource"() {
        given: "valid input"
            def user = UserId.from(faker.name().username())
            def resource = givenResource(user)

        expect: "resource is saved and an id is assigned"
            def result = resourceRepository.save(resource)
            result.id != null
            result.description == resource.description
            result.name == resource.name
            result.belongsTo(user)
    }

    def "should retrieve a resource entity created by the same user"() {
        given: "a resource saved by a user"
            def aUser = UserId.from(faker.name().username())
            def resourceOfUser = resourceRepository.save(givenResource(aUser))
            entityManager.flush()

        expect: "the resource entity is retrieved for the same user"
            def result = resourceRepository.findByIdAndOwnerId(resourceOfUser.id, aUser)
            result.isPresent()
            result.get().id == resourceOfUser.id
            result.get().name == resourceOfUser.name
            result.get().description == resourceOfUser.description
            result.get().quantityUnit == resourceOfUser.quantityUnit
    }

    def "should retrieve a resource details created by the same user"() {
        given: "a resource saved by a user"
            def aUser = UserId.from(faker.name().username())
            def resourceOfUser = resourceRepository.save(givenResource(aUser))
            entityManager.flush()

        expect: "the resource details is retrieved for the same user"
            def result = resourceRepository.findDetailsByIdAndOwnerId(resourceOfUser.id, aUser)
            result.isPresent()
            result.get().id == resourceOfUser.id
            result.get().name == resourceOfUser.name.asString()
            result.get().description == resourceOfUser.description.asString()
            result.get().quantityUnit == resourceOfUser.quantityUnit
    }


    def "should not retrieve a resource created by another user"() {
        given: "a resource saved by a user"
            def aUser = UserId.from(faker.name().username())
            def resourceOfUser = resourceRepository.save(givenResource(aUser))

        when: "another user tries to retrieve the resource"
            def anotherUser = UserId.from("anotherUser")
            def result = resourceRepository.findByIdAndOwnerId(resourceOfUser.id, anotherUser)

        then: "no result"
            result.isEmpty()
    }

    def "should retrieve all resources of the given user only"() {
        given: "some resources which belong to user"
            def aUser = UserId.from(faker.name().username())
            def resource1OfUser = resourceRepository.save(givenResource(aUser))
            def resource2OfUser = resourceRepository.save(givenResource(aUser))
            def resource3OfUser = resourceRepository.save(givenResource(aUser))

        and: "some resources which belong to another user"
            def anotherUser = UserId.from("anotherUser")
            def resource1OfAnotherUser = resourceRepository.save(givenResource(anotherUser))

        expect: "all resources which belong to the user are retrieved and not the one from another user"
            def resourcesOfUser = resourceRepository.findAllByOwnerId(aUser)
            resourcesOfUser hasSize(3)
            resourcesOfUser*.id == [resource1OfUser.id, resource2OfUser.id, resource3OfUser.id]
            resourcesOfUser*.name == [resource1OfUser.name.asString(), resource2OfUser.name.asString(), resource3OfUser.name.asString()]
            resourcesOfUser*.id.every { it != resource1OfAnotherUser.id }
    }

}
