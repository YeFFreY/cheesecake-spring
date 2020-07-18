package org.yeffrey.cheesecakespring.users.stories

import org.yeffrey.cheesecakespring.users.BaseSpecification
import org.yeffrey.cheesecakespring.users.dto.RegisterCommand

class UserStoriesSpec extends BaseSpecification {

    def stories = new UserStories(userRepository)

    def newRegisterCommand() {
        def command = new RegisterCommand()
        command.setEmail(faker.internet().emailAddress())
        command.setUsername(faker.name().username())
        command.setPassword(faker.internet().password())
        return command
    }

    def "registered user can only be done once"() {
        given: "a register command"
            def command = newRegisterCommand()

        when: "a user register"
            def user = stories.register(command)

        then: "user receives an id and cannot register anymore"
            user.id != null;
            stories.register(command) == null
    }
}
