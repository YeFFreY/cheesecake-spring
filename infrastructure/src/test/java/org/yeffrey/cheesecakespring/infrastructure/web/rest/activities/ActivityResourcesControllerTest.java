package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Transactional
class ActivityResourcesControllerTest implements ActivitiesEndpoint, ResourcesEndpoint, ActivityResourcesEndpoint {
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
            .andExpect(jsonPath("$[*].quantity", containsInAnyOrder(cmd.quantity)))
            .andDo(print());
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
            .andExpect(jsonPath("$", hasSize(0)))
            .andDo(print());
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
            .andExpect(jsonPath("$", hasSize(0)))
            .andDo(print());
    }
}