package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;

import javax.annotation.Nullable;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface LibrariesEndpoint {

    MockMvc getMvc();

    ObjectMapper getMapper();


    default EntityId userLibrary(@Nullable String userId) throws Exception {
        MockHttpServletRequestBuilder post = post("/api/libraries");

        if (Objects.nonNull(userId)) {
            post = post.with(user(userId));
        }

        String response = getMvc().perform(post
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        return getMapper().readValue(response, EntityId.class);
    }

    default EntityId userLibrary() throws Exception {
        return this.userLibrary(null);
    }
}
