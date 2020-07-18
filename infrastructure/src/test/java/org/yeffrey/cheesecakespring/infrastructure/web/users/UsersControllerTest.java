package org.yeffrey.cheesecakespring.infrastructure.web.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.infrastructure.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.users.endpoints.UsersEndpoint;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsersControllerTest extends RestIntegrationTest implements UsersEndpoint {

    @Override
    public MockMvc getMvc() {
        return this.mvc;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    @Test
    void guestShouldSeeLoginPage() throws Exception {
        loginPage()
            .andExpect(status().isOk());
    }

    @Test
    void guestShouldSeeRegisterPage() throws Exception {
        registerPage()
            .andExpect(status().isOk());
    }

    @Test
    void guestCanRegister() throws Exception {
        register(faker.name().username(), faker.internet().emailAddress(), faker.internet().password())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/app"));
    }
}