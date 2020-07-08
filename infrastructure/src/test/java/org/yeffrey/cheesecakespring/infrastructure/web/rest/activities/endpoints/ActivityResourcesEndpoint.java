package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.library.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.library.dto.AdjustActivityResourceQuantityCommand;

import javax.annotation.Nullable;
import java.util.Objects;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    default ResultActions removeActivityResource(EntityId activityId, EntityId resourceId, @Nullable String userId) throws Exception {
        MockHttpServletRequestBuilder delete = delete("/api/activities/{id}/resources/{resourceId}", activityId.getId(), resourceId.getId());

        if (Objects.nonNull(userId)) {
            delete = delete.with(user(userId));
        }

        return getMvc().perform(delete.contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));
    }

    default ResultActions removeActivityResource(EntityId activityId, EntityId resourceId) throws Exception {
        return removeActivityResource(activityId, resourceId, null);
    }

    default ResultActions showActivityResources(EntityId entityId) throws Exception {
        return getMvc().perform(get("/api/activities/{id}/resources", entityId.getId())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));
    }

    default ResultActions adjustActivityResourceQuantity(EntityId activityId, EntityId resourceId, AdjustActivityResourceQuantityCommand command) throws Exception {
        return adjustActivityResourceQuantity(activityId, resourceId, command, null);
    }

    default ResultActions adjustActivityResourceQuantity(EntityId activityId, EntityId resourceId, AdjustActivityResourceQuantityCommand command, @Nullable String userId) throws Exception {
        MockHttpServletRequestBuilder put = put("/api/activities/{id}/resources/{resourceId}", activityId.getId(), resourceId.getId());

        if (Objects.nonNull(userId)) {
            put = put.with(user(userId));
        }

        return getMvc().perform(put.content(getMapper().writeValueAsString(command))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON));
    }

}
