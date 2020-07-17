package org.yeffrey.cheesecakespring.infrastructure.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/app")
    public String home() {
        return "home/index";
    }
}
