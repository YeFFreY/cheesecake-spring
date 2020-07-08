package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.infrastructure.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ActivitiesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.utils.JsonTestUtils;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ActivitiesControllerTest extends RestIntegrationTest implements ActivitiesEndpoint {

    @Override
    public MockMvc getMvc() {
        return this.mvc;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    private CreateUpdateActivityCommand givenACreateUpdateCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph());
    }


    @Test
    @WithMockUser
    void userShouldRetrieveAnActivityHeCreated() throws Exception {
        CreateUpdateActivityCommand command = givenACreateUpdateCommand();
        EntityId entityId = newActivity(command);
        assertThat(entityId).isNotNull();

        showActivity(entityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(entityId.getId()))
            .andExpect(jsonPath("$.name").value(command.name))
            .andExpect(jsonPath("$.description").value(command.description))
            .andExpect(JsonTestUtils.hasLinksCount(2))
            .andExpect(JsonTestUtils.hasLink("self"))
            .andExpect(JsonTestUtils.hasLink("update"));

    }

/*
    @Test
    @WithMockUser
    void userShouldNotRetrieveAnActivityCreatedByAnotherUser() throws Exception {
        EntityId anotherUserActivity = newActivity(givenACreateUpdateCommand(), "anotherUser");

        showActivity(anotherUserActivity).andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());

    }
*/

    @Test
    @WithMockUser
    void userShouldUpdateAnActivityHeCreated() throws Exception {
        CreateUpdateActivityCommand command = givenACreateUpdateCommand();
        EntityId entityId = newActivity(command);

        CreateUpdateActivityCommand updateCommand = givenACreateUpdateCommand();
        updateActivity(entityId, updateCommand);

        showActivity(entityId)
            .andExpect(jsonPath("$.id").value(entityId.getId()))
            .andExpect(jsonPath("$.name").value(updateCommand.name))
            .andExpect(jsonPath("$.description").value(updateCommand.description));

    }

    @Test
    @WithMockUser
    void userShouldRetrieveAllActivitiesHeCreated() throws Exception {
        CreateUpdateActivityCommand command = givenACreateUpdateCommand();
        EntityId entityId = newActivity(command);
        CreateUpdateActivityCommand anotherCommand = givenACreateUpdateCommand();
        EntityId anotherEntityId = newActivity(anotherCommand);

        showActivities()
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(entityId.getId().intValue(), anotherEntityId.getId().intValue())))
            .andExpect(jsonPath("$[*].name", containsInAnyOrder(command.name, anotherCommand.name)))
            .andExpect(jsonPath("$[*]._links", everyItem(hasKey("self"))));

    }

}