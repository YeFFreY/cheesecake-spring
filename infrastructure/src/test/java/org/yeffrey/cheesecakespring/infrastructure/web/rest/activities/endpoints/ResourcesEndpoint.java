package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;

import javax.annotation.Nullable;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface ResourcesEndpoint {

    MockMvc getMvc();

    ObjectMapper getMapper();


    default EntityId newResource(CreateUpdateResourceCommand command, @Nullable String userId) throws Exception {
        MockHttpServletRequestBuilder post = post("/api/resources");

        if (Objects.nonNull(userId)) {
            post = post.with(user(userId));
        }

        String response = getMvc().perform(post
                                               .content(getMapper().writeValueAsString(command))
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        return getMapper().readValue(response, EntityId.class);
    }

    default EntityId newResource(CreateUpdateResourceCommand command) throws Exception {
        return this.newResource(command, null);
    }

    default ResultActions showResource(EntityId entityId) throws Exception {
        return getMvc().perform(get("/api/resources/{id}", entityId.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));

    }

    default ResultActions showResources() throws Exception {
        return getMvc().perform(get("/api/resources")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    default void updateResource(EntityId entityId, CreateUpdateResourceCommand command) throws Exception {
        getMvc().perform(put("/api/resources/{id}", entityId.getId())
                             .content(getMapper().writeValueAsString(command))
                             .contentType(MediaType.APPLICATION_JSON)
                             .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
