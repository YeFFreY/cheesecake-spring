package org.yeffrey.cheesecakespring.infrastructure.web.users;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.yeffrey.cheesecakespring.users.domain.User;
import org.yeffrey.cheesecakespring.users.dto.LoginCommand;
import org.yeffrey.cheesecakespring.users.dto.RegisterCommand;
import org.yeffrey.cheesecakespring.users.stories.UserStories;

import javax.validation.Valid;

@Controller
public class UsersController {

    public static final String URL_REGISTER = "user/register";
    private final UserStories userStories;

    public UsersController(UserStories userStories) {
        this.userStories = userStories;
    }

    @GetMapping("/login")
    public String login(LoginCommand loginCommand) {
        return "user/login";
    }

    @GetMapping("/user/register")
    public String register(RegisterCommand registerCommand) {
        return URL_REGISTER;
    }

    @PostMapping(value = "/user/register")
    public String registerPost(@Valid final RegisterCommand command,
                               final BindingResult result) {

        if (result.hasErrors()) {
            return URL_REGISTER;
        }

        User registeredUser = userStories.register(command);
        if (registeredUser != null) {
            userStories.autoLogin(registeredUser.getUsername());
            return "redirect:/app";
        } else {
            // FIXME Not sure I want to handle "user already exists by returning null from "register(username)"
            result.rejectValue("email", "error.alreadyExists", "This username or email already exists, please try to reset password instead.");
            return URL_REGISTER;
        }
    }
}
