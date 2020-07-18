package org.yeffrey.cheesecakespring.infrastructure.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.common.domain.UserId
import org.yeffrey.cheesecakespring.library.domain.Library
import org.yeffrey.cheesecakespring.library.domain.LibrarySamples

import static org.hamcrest.Matchers.hasSize

class ResourceRepositorySpec extends IntegrationSpecification implements LibrarySamples {
    @Autowired
    private TestEntityManager entityManager

    @Autowired
    LibraryRepositoryAdapter libraryRepository

    @Autowired
    ResourceRepositoryAdapter resourceRepository

    Library library

    def setup() {
        library = libraryRepository.save(givenLibrary(faker.name().username()))
    }

    def "should save a resource"() {
        given: "valid input"
            def resource = givenResource(library)

        expect: "resource is saved and an id is assigned"
            def result = resourceRepository.save(resource)
            result.id != null
            result.description == resource.description
            result.name == resource.name
    }

    def "should retrieve a resource entity"() {
        given: "a resource saved"
            def resource = resourceRepository.save(givenResource(library))
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
            def resource = resourceRepository.save(givenResource(library))
            entityManager.flush()

        expect: "the resource details is retrieved"
            def result = resourceRepository.findDetailsById(resource.id)
            result.isPresent()
            result.get().id == resource.id
            result.get().name == resource.name.asString()
            result.get().description == resource.description.asString()
            result.get().quantityUnit == resource.quantityUnit
    }

    def "should retrieve all resources from given library"() {
        given: "some resources"
            def resource = resourceRepository.save(givenResource(library))
            def resource2 = resourceRepository.save(givenResource(library))
            def resource3 = resourceRepository.save(givenResource(library))

        expect: "all resources are retrieved"
            def resourcesOfUser = resourceRepository.findResourcesByLibrary(library)
            resourcesOfUser hasSize(3)
            resourcesOfUser*.id == [resource.id, resource2.id, resource3.id]
            resourcesOfUser*.name == [resource.name.asString(), resource2.name.asString(), resource3.name.asString()]
    }

    def "should tell that the resource belongs to library"() {
        given: "some activities"
            def resource = resourceRepository.save(givenResource(library))
            flushAndClear()

        expect: "activity belongs to library"
            def belongs = resourceRepository.resourceBelongsToUserLibrary(resource.id, library.ownerId)
            belongs
    }

    def "should tell that the resource doesnt belong to library"() {
        given: "some activities"
            def anotherLibrary = libraryRepository.save(Library.from(UserId.from("anotherUser")))
            def resource = resourceRepository.save(givenResource(anotherLibrary))
            flushAndClear()

        expect: "activity doesnt belong to library"
            def belongs = resourceRepository.resourceBelongsToUserLibrary(resource.id, library.ownerId)
            !belongs
    }

}
