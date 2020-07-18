package org.yeffrey.cheesecakespring.users.domain

import org.yeffrey.cheesecakespring.library.domain.UsersSamples
import org.yeffrey.cheesecakespring.users.BaseSpecification
import spock.lang.Unroll

class UserSpec extends BaseSpecification implements UsersSamples {

    @Unroll
    def "should refuse to create a new user because #problem"() {
        when: "creating a user"
            User.from(username, password, email)

        then: "fails"
            thrown IllegalArgumentException

        where: "input is invalid"
            problem            | username                | password                    | email
            "username is null" | null                    | faker.internet().password() | faker.internet().emailAddress()
            "password is null" | faker.name().username() | null                        | faker.internet().emailAddress()
            "email is null"    | faker.name().username() | faker.internet().password() | null
    }

}
