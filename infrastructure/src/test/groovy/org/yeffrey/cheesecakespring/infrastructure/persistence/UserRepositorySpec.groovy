package org.yeffrey.cheesecakespring.infrastructure.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.yeffrey.cheesecakespring.library.domain.UsersSamples

class UserRepositorySpec extends IntegrationSpecification implements UsersSamples {
    @Autowired
    private TestEntityManager entityManager

    @Autowired
    UserRepositoryAdapter userRepository

    def "should save a user"() {
        given: "valid input"
            def user = givenUser(faker.name().username(), faker.internet().emailAddress(), faker.internet().password())

        expect: "user is saved and an id is assigned"
            def result = userRepository.save(user)
            flushAndClear()
            result.id != null
            result.email == user.email
            result.username == user.username
            result.password == user.password
    }


    def "should retrieve an activity entity"() {
        given: "a user saved"
            def user = userRepository.save(givenUser(faker.name().username(), faker.internet().emailAddress(), faker.internet().password()))
            flushAndClear()

        expect: "the user entity is retrieved"
            def result = userRepository.findByUserName(user.username)
            result.isPresent()
            result.get().id == user.id
            result.get().username == user.username
            result.get().email == user.email
    }

    def "should tell if user exists by email"() {
        given: "a user saved"
            def user = userRepository.save(givenUser(faker.name().username(), faker.internet().emailAddress(), faker.internet().password()))
            flushAndClear()

        expect: "the user entity is found"
            def result = userRepository.existsByEmail(user.email)
            result
    }

    def "should tell if user does not exists by email"() {
        given: "a user saved"
            userRepository.save(givenUser(faker.name().username(), faker.internet().emailAddress(), faker.internet().password()))
            flushAndClear()

        expect: "the user entity is found"
            def result = userRepository.existsByEmail(faker.internet().emailAddress())
            !result
    }

    def "should tell if user exists by username"() {
        given: "a user saved"
            def user = userRepository.save(givenUser(faker.name().username(), faker.internet().emailAddress(), faker.internet().password()))
            flushAndClear()

        expect: "the user entity is found"
            def result = userRepository.existsByUserName(user.username)
            result
    }

    def "should tell if user does not exists by username"() {
        given: "a user saved"
            userRepository.save(givenUser(faker.name().username(), faker.internet().emailAddress(), faker.internet().password()))
            flushAndClear()

        expect: "the user entity is found"
            def result = userRepository.existsByUserName(faker.name().username())
            !result
    }

    def "should encode password"() {
        given: "a password"
            def password = faker.internet().password()
        expect: "the password is encoded"
            def encodedPassword = userRepository.encodePassword(password)
            encodedPassword != null

    }
}
