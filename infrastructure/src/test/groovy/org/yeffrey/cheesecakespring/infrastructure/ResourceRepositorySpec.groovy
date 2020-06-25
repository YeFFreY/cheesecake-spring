package org.yeffrey.cheesecakespring.infrastructure

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.activities.Resource
import org.yeffrey.cheesecakespring.activities.ResourceQuantityUnit
import org.yeffrey.cheesecakespring.activities.ResourceRepository
import org.yeffrey.cheesecakespring.activities.domain.ResourceDescription
import org.yeffrey.cheesecakespring.activities.domain.ResourceName
import org.yeffrey.cheesecakespring.activities.domain.UserId
import spock.lang.Shared
import spock.lang.Unroll

import static org.hamcrest.Matchers.hasSize

class ResourceRepositorySpec extends IntegrationSpecification {
    @Shared
    def faker = new Faker()

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ResourceRepository resourceRepository

    def "should save a resource"() {
        given: "valid input"
            def name = ResourceName.from(faker.lorem().sentence())
            def description = ResourceDescription.from(faker.lorem().paragraph())
            def username = UserId.from(faker.name().username())
            def qtyUnit = ResourceQuantityUnit.Item

        expect: "resource is saved and an id is assigned"
            def result = resourceRepository.save(Resource.from(name, description, qtyUnit, username))
            result.id != null
            result.description == description
            result.name == name
            result.belongsTo(username)
    }


    @Unroll
    def "should refuse when saving new resource because #problem"() {
        when: "saving a resource"
            def result = resourceRepository.save(Resource.from(name, description, quantityUnit, username))
            entityManager.flush()

        then: "fails"
            result == null
            thrown NullPointerException

        where: "input is invalid"
            problem                 | name                                        | description                                         | quantityUnit              | username
            "name is null"          | null                                        | ResourceDescription.from(faker.lorem().paragraph()) | ResourceQuantityUnit.Item | UserId.from(faker.name().username())
            "username is null"      | ResourceName.from(faker.lorem().sentence()) | ResourceDescription.from(faker.lorem().paragraph()) | ResourceQuantityUnit.Item | null
            "quantity unit is null" | ResourceName.from(faker.lorem().sentence()) | ResourceDescription.from(faker.lorem().paragraph()) | null                      | UserId.from(faker.name().username())
    }

    def "should retrieve a resource entity created by the same user"() {
        given: "a resource saved by a user"
            def aUser = UserId.from(faker.name().username())
            def resourceOfUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))
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
            def resourceOfUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))
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
            def resourceOfUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))

        when: "another user tries to retrieve the resource"
            def anotherUser = UserId.from("anotherUser")
            def result = resourceRepository.findByIdAndOwnerId(resourceOfUser.id, anotherUser)

        then: "no result"
            result.isEmpty()
    }

    def "should retrieve all resources of the given user only"() {
        given: "some resources which belong to user"
            def aUser = UserId.from(faker.name().username())
            def resource1OfUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))
            def resource2OfUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))
            def resource3OfUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, aUser))

        and: "some resources which belong to another user"
            def anotherUser = UserId.from("anotherUser")
            def resource1OfAnotherUser = resourceRepository.save(Resource.from(ResourceName.from(faker.lorem().sentence()), ResourceDescription.from(faker.lorem().paragraph()), ResourceQuantityUnit.Item, anotherUser))

        expect: "all resources which belong to the user are retrieved and not the one from another user"
            def resourcesOfUser = resourceRepository.findAllByOwnerId(aUser)
            resourcesOfUser hasSize(3)
            resourcesOfUser*.id == [resource1OfUser.id, resource2OfUser.id, resource3OfUser.id]
            resourcesOfUser*.name == [resource1OfUser.name.asString(), resource2OfUser.name.asString(), resource3OfUser.name.asString()]
            resourcesOfUser*.id.every { it != resource1OfAnotherUser.id }
    }

}
