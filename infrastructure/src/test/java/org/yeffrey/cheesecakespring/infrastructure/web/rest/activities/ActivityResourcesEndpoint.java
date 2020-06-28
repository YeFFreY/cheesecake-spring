package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;

import javax.annotation.Nullable;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public interface ActivityResourcesEndpoint {

    MockMvc getMvc();

    ObjectMapper getMapper();


    default ResultActions addActivityResource(EntityId entityId, AddResourceToActivityCommand command, @Nullable String userId) throws Exception {
        MockHttpServletRequestBuilder post = post("/api/activities/{id}/resources", entityId.getId());

        if (Objects.nonNull(userId)) {
            post = post.with(user(userId));
        }

        return getMvc().perform(post.content(getMapper().writeValueAsString(command))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));
    }

    default ResultActions addActivityResource(EntityId entityId, AddResourceToActivityCommand command) throws Exception {
        return addActivityResource(entityId, command, null);
    }

    default ResultActions showActivityResources(EntityId entityId) throws Exception {
        return getMvc().perform(get("/api/activities/{id}/resources", entityId.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));

    }
}
