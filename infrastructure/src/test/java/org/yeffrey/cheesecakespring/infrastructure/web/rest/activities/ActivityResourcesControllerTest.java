package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.activities.dto.AdjustActivityResourceQuantityCommand;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ActivitiesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ActivityResourcesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ResourcesEndpoint;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ActivityResourcesControllerTest extends RestIntegrationTest
    implements ActivitiesEndpoint, ResourcesEndpoint, ActivityResourcesEndpoint {

    Faker faker = new Faker();

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

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

    private AdjustActivityResourceQuantityCommand givenAdjustActivityResourceQtyCommand() {
        return new AdjustActivityResourceQuantityCommand(faker.number().numberBetween(1, 100));
    }

    private AdjustActivityResourceQuantityCommand givenAdjustActivityResourceQtyCommandWithInvalidValue() {
        return new AdjustActivityResourceQuantityCommand(-10);
    }

    @Test
    @WithMockUser
    public void userCanAddResourceToExistingActivity() throws Exception {
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
    public void userCannotAddResourceToActivityFromAnotherUser() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd, "anotherUser")
            .andExpect(status().isNotFound());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    @WithMockUser
    public void userCannotAddHisResourceToActivityFromAnotherUser() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand(), "anotherUser");

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd, "anotherUser")
            .andExpect(status().isNotFound());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void userCanRemoveResourceFromExistingActivity() throws Exception {
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
    public void userCannotRemoveResourceFromAnotherUserActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        var cmd = new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero());
        addActivityResource(activityId, cmd)
            .andExpect(status().isCreated());

        removeActivityResource(activityId, resourceId, "anotherUser")
            .andExpect(status().isNotFound()); // another user has no access to activity or resource of user

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    public void RemoveResourceNotAssociatedFromExistingActivityIsBadRequestAndNoImpactOnActivity() throws Exception {
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

    @Test
    @WithMockUser
    public void userCanUpdateResourceQuantityOfExistingActivity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        addActivityResource(activityId, new AddResourceToActivityCommand(resourceId.getId(), faker.number().randomDigitNotZero()))
            .andExpect(status().isCreated());

        var cmd = givenAdjustActivityResourceQtyCommand();
        adjustActivityResourceQuantity(activityId, resourceId, cmd)
            .andExpect(status().isOk());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(resourceId.getId().intValue())))
            .andExpect(jsonPath("$[*].quantity", containsInAnyOrder(cmd.quantity)));
    }

    @Test
    @WithMockUser
    public void userCannotUpdateResourceQuantityOfExistingActivityWithInvalidQuantity() throws Exception {
        EntityId activityId = newActivity(givenNewActivityCommand());
        EntityId resourceId = newResource(givenNewResourceCommand());

        int initialQuantity = faker.number().randomDigitNotZero();
        addActivityResource(activityId, new AddResourceToActivityCommand(resourceId.getId(), initialQuantity))
            .andExpect(status().isCreated());

        adjustActivityResourceQuantity(activityId, resourceId, givenAdjustActivityResourceQtyCommandWithInvalidValue())
            .andExpect(status().isBadRequest());

        showActivityResources(activityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(resourceId.getId().intValue())))
            .andExpect(jsonPath("$[*].quantity", containsInAnyOrder(initialQuantity)));
    }

    @Test
    @WithMockUser
    public void userCannotUpdateResourceQuantityOfAnotherUserActivity() throws Exception {
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
    }
}