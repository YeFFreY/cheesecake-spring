package org.yeffrey.cheesecakespring.infrastructure.web.users.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public interface UsersEndpoint {

    MockMvc getMvc();

    ObjectMapper getMapper();

    default ResultActions loginPage() throws Exception {
        return getMvc().perform(get("/login"));
    }

    default ResultActions registerPage() throws Exception {
        return getMvc().perform(get("/user/register"));
    }

    default ResultActions register(String username,
                                   String email,
                                   String password) throws Exception {
        return getMvc().perform(post("/user/register")
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                    .param("username", username)
                                    .param("email", email)
                                    .param("password", password));
    }
}
