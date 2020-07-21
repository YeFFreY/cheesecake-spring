package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.infrastructure.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ActivitiesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ActivityResourcesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.LibrariesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ResourcesEndpoint;
import org.yeffrey.cheesecakespring.library.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ActivityResourcesControllerTest extends RestIntegrationTest
    implements ActivitiesEndpoint, ResourcesEndpoint, ActivityResourcesEndpoint, LibrariesEndpoint {

    @Override
    public MockMvc getMvc() {
        return this.mvc;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    // FIXME Move this (and those in other controllers) in super class
    private CreateUpdateActivityCommand givenNewActivityCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph());
    }

    private CreateUpdateResourceCommand givenNewResourceCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item");
    }

    @BeforeEach
    public void givenLibrary() throws Exception {
        userLibrary();
        userLibrary("anotherUser");
    }

    @Test
    @WithMockUser
    void userCanAddResourceToExistingActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd)
            .andExpect(status().isCreated());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(cmd.resourceId.intValue())))
            .andExpect(jsonPath("$[*].quantity", containsInAnyOrder(cmd.quantity)));
    }


    @Test
    @WithMockUser
    void userCannotAddResourceToActivityFromAnotherUser() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd, "anotherUser")
            .andExpect(status().isForbidden());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    @WithMockUser
    void userCannotAddHisResourceToActivityFromAnotherUser() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand(), "anotherUser");

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd, "anotherUser")
            .andExpect(status().isForbidden());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    void userCanRemoveResourceFromExistingActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd)
            .andExpect(status().isCreated());

        removeActivityResource(activityId, resourceId)
            .andExpect(status().isOk());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    void userCannotRemoveResourceFromAnotherUserActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd)
            .andExpect(status().isCreated());

        removeActivityResource(activityId, resourceId, "anotherUser")
            .andExpect(status().isForbidden()); // another user has no access to activity or resource of user

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    void RemoveResourceNotAssociatedFromExistingActivityIsBadRequestAndNoImpactOnActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());
        EntityId resourceIdNotAssociated = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd)
            .andExpect(status().isCreated());

        removeActivityResource(activityId, resourceIdNotAssociated)
            .andExpect(status().isBadRequest());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(cmd.resourceId.intValue())));
    }

/*    @Test
    @WithMockUser
    void userCannotUpdateResourceQuantityOfAnotherUserActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        int initialQuantity = faker.number().randomDigitNotZero();
        addActivityResource(activityId, new AddResourceToActivityCommand(resourceId.getId(), initialQuantity))
            .andExpect(status().isCreated());

        var cmd = givenAdjustActivityResourceQtyCommand();
        adjustActivityResourceQuantity(activityId, resourceId, cmd, "anotherUser")
            .andExpect(status().isNotFound());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(resourceId.getId().intValue())))
            .andExpect(jsonPath("$[*].quantity", containsInAnyOrder(initialQuantity)));
    }*/
}