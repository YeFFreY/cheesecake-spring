package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.infrastructure.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.LibrariesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.ResourcesEndpoint;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.utils.JsonTestUtils;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourcesControllerTest extends RestIntegrationTest implements ResourcesEndpoint, LibrariesEndpoint {

    @Override
    public MockMvc getMvc() {
        return this.mvc;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    private CreateUpdateResourceCommand givenACreateUpdateCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item");
    }

    @BeforeEach
    public void givenLibrary() throws Exception {
        userLibrary();
        userLibrary(this.anotherUser);
    }

    @Test
    @WithMockUser
    void userShouldRetrieveAnActivityHeCreated() throws Exception {
        CreateUpdateResourceCommand command = givenACreateUpdateCommand();
        EntityId entityId = newResource(command);
        assertThat(entityId).isNotNull();

        showResource(entityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(entityId.getId()))
            .andExpect(jsonPath("$.name").value(command.name))
            .andExpect(jsonPath("$.description").value(command.description))
            .andExpect(jsonPath("$.quantityUnit").value(command.quantityUnit))
            .andExpect(JsonTestUtils.hasLinksCount(2))
            .andExpect(JsonTestUtils.hasLink("self"))
            .andExpect(JsonTestUtils.hasLink("update"));

    }

    @Test
    @WithMockUser
    void userShouldNotRetrieveAnResourceCreatedByAnotherUser() throws Exception {
        EntityId anotherUserResource = newResource(givenACreateUpdateCommand(), this.anotherUser);

        showResource(anotherUserResource).andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void userShouldUpdateAnResourceHeCreated() throws Exception {
        CreateUpdateResourceCommand command = givenACreateUpdateCommand();
        EntityId entityId = newResource(command);

        CreateUpdateResourceCommand updateCommand = givenACreateUpdateCommand();
        updateResource(entityId, updateCommand).andExpect(status().isOk());

        showResource(entityId)
            .andExpect(jsonPath("$.id").value(entityId.getId()))
            .andExpect(jsonPath("$.name").value(updateCommand.name))
            .andExpect(jsonPath("$.description").value(updateCommand.description))
            .andExpect(jsonPath("$.quantityUnit").value(updateCommand.quantityUnit));

    }

    @Test
    @WithMockUser
    void userShouldNotUpdateAnResourceAnotherUserCreated() throws Exception {
        CreateUpdateResourceCommand command = givenACreateUpdateCommand();
        EntityId entityId = newResource(command, this.anotherUser);

        CreateUpdateResourceCommand updateCommand = givenACreateUpdateCommand();
        updateResource(entityId, updateCommand).andExpect(status().isForbidden())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    void userShouldRetrieveAllResourcesHeCreatedAndNotOthers() throws Exception {
        CreateUpdateResourceCommand command = givenACreateUpdateCommand();
        EntityId entityId = newResource(command);
        CreateUpdateResourceCommand anotherCommand = givenACreateUpdateCommand();
        EntityId anotherEntityId = newResource(anotherCommand);
        newResource(givenACreateUpdateCommand(), this.anotherUser);

        showResources()
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(entityId.getId().intValue(), anotherEntityId.getId().intValue())))
            .andExpect(jsonPath("$[*].name", containsInAnyOrder(command.name, anotherCommand.name)))
            .andExpect(jsonPath("$[*]._links", everyItem(hasKey("self"))));

    }

}