package org.yeffrey.cheesecakespring.features.activities.domain

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException

import static org.hamcrest.Matchers.hasSize

@ActiveProfiles("integration")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ResourceRepositorySpec extends Specification {
    @Shared
    def faker = new Faker()

    @Autowired
    private TestEntityManager entityManager

    @Autowired
    ResourceRepository resourceRepository

    def "should save a resource"() {
        given: "valid input"
            def name = faker.lorem().sentence()
            def description = faker.lorem().paragraph()
            def username = faker.name().username()
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
            resourceRepository.save(Resource.from(name, description, quantityUnit, username))
            entityManager.flush()

        then: "fails"
            thrown ConstraintViolationException

        where: "input is invalid"
            problem                    | name                          | description               | quantityUnit              | username
            "name is null"             | null                          | faker.lorem().paragraph() | ResourceQuantityUnit.Item | faker.name().username()
            "name is empty string"     | ""                            | faker.lorem().paragraph() | ResourceQuantityUnit.Item | faker.name().username()
            "name is blank string"     | "  "                          | faker.lorem().paragraph() | ResourceQuantityUnit.Item | faker.name().username()
            "name is too long"         | faker.lorem().characters(256) | faker.lorem().paragraph() | ResourceQuantityUnit.Item | faker.name().username()
            "username is null"         | faker.lorem().sentence()      | faker.lorem().paragraph() | ResourceQuantityUnit.Item | null
            "username is empty string" | faker.lorem().sentence()      | faker.lorem().paragraph() | ResourceQuantityUnit.Item | ""
            "username is blank string" | faker.lorem().sentence()      | faker.lorem().paragraph() | ResourceQuantityUnit.Item | "   "
            "username is too long"     | faker.lorem().sentence()      | faker.lorem().paragraph() | ResourceQuantityUnit.Item | faker.lorem().characters(256)
            "quantity unit is null"    | faker.lorem().sentence()      | faker.lorem().paragraph() | null                      | faker.name().username()
    }

    def "should retrieve a resource entity created by the same user"() {
        given: "a resource saved by a user"
            def aUser = faker.name().username()
            def resourceOfUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, aUser))
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
            def aUser = faker.name().username()
            def resourceOfUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, aUser))
            entityManager.flush()

        expect: "the resource details is retrieved for the same user"
            def result = resourceRepository.findDetailsByIdAndOwnerId(resourceOfUser.id, aUser)
            result.isPresent()
            result.get().id == resourceOfUser.id
            result.get().name == resourceOfUser.name
            result.get().description == resourceOfUser.description
            result.get().quantityUnit == resourceOfUser.quantityUnit.name()
    }


    def "should not retrieve a resource created by another user"() {
        given: "a resource saved by a user"
            def resourceOfUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, "user"))

        when: "another user tries to retrieve the resource"
            def result = resourceRepository.findByIdAndOwnerId(resourceOfUser.id, "anotherUser")

        then: "no result"
            result.isEmpty()
    }

    def "should retrieve all resources of the given user only"() {
        given: "some resources which belong to user"
            def aUser = "user"
            def resource1OfUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, aUser))
            def resource2OfUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, aUser))
            def resource3OfUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, aUser))

        and: "some resources which belong to another user"
            def resource1OfAnotherUser = resourceRepository.save(Resource.from(faker.lorem().sentence(), faker.lorem().paragraph(), ResourceQuantityUnit.Item, "anotherUser"))

        expect: "all resources which belong to the user are retrieved"
            def resourcesOfUser = resourceRepository.findAllByOwnerId(aUser)
            resourcesOfUser hasSize(3)
            resourcesOfUser*.id == [resource1OfUser.id, resource2OfUser.id, resource3OfUser.id]
            resourcesOfUser*.name == [resource1OfUser.name, resource2OfUser.name, resource3OfUser.name]
            resourcesOfUser*.id.every { it != resource1OfAnotherUser.id }
    }

}
