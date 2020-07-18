package org.yeffrey.cheesecakespring.library.domain

import com.github.javafaker.Faker
import groovy.transform.CompileStatic
import org.yeffrey.cheesecakespring.users.domain.User

@CompileStatic
trait UsersSamples {
    static Faker faker = new Faker()


    static User givenUser(String username, String email, String password) {
        return User.from(username, password, email);
    }
}