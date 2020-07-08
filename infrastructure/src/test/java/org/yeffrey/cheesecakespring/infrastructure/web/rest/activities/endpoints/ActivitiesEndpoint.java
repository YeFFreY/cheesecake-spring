package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand;

import javax.annotation.Nullable;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface ActivitiesEndpoint {

    MockMvc getMvc();

    ObjectMapper getMapper();


    default EntityId newActivity(CreateUpdateActivityCommand command, @Nullable String userId) throws Exception {
        MockHttpServletRequestBuilder post = post("/api/activities");

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

    default EntityId newActivity(CreateUpdateActivityCommand command) throws Exception {
        return this.newActivity(command, null);
    }

    default ResultActions showActivity(EntityId entityId) throws Exception {
        return getMvc().perform(get("/api/activities/{id}", entityId.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));

    }

    default ResultActions showActivities() throws Exception {
        return getMvc().perform(get("/api/activities")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    default void updateActivity(EntityId entityId, CreateUpdateActivityCommand command) throws Exception {
        getMvc().perform(put("/api/activities/{id}", entityId.getId())
                             .content(getMapper().writeValueAsString(command))
                             .contentType(MediaType.APPLICATION_JSON)
                             .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
