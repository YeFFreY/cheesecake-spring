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
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.utils.JsonTestUtils;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Transactional
class ResourcesControllerTest implements ResourcesEndpoint {
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

    private CreateUpdateResourceCommand givenACreateUpdateCommand() {
        return new CreateUpdateResourceCommand(faker.lorem().sentence(), faker.lorem().paragraph(), "Item");
    }


    @Test
    @WithMockUser
    public void userShouldRetrieveAnActivityHeCreated() throws Exception {
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
    public void userShouldNotRetrieveAnResourceCreatedByAnotherUser() throws Exception {
        EntityId anotherUserResource = newResource(givenACreateUpdateCommand(), "anotherUser");

        showResource(anotherUserResource).andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser
    public void userShouldUpdateAnResourceHeCreated() throws Exception {
        CreateUpdateResourceCommand command = givenACreateUpdateCommand();
        EntityId entityId = newResource(command);

        CreateUpdateResourceCommand updateCommand = givenACreateUpdateCommand();
        updateResource(entityId, updateCommand);

        showResource(entityId)
            .andExpect(jsonPath("$.id").value(entityId.getId()))
            .andExpect(jsonPath("$.name").value(updateCommand.name))
            .andExpect(jsonPath("$.description").value(updateCommand.description))
            .andExpect(jsonPath("$.quantityUnit").value(updateCommand.quantityUnit));

    }

    @Test
    @WithMockUser
    public void userShouldRetrieveAllResourcesHeCreated() throws Exception {
        CreateUpdateResourceCommand command = givenACreateUpdateCommand();
        EntityId entityId = newResource(command);
        CreateUpdateResourceCommand anotherCommand = givenACreateUpdateCommand();
        EntityId anotherEntityId = newResource(anotherCommand);

        showResources()
            .andExpect(jsonPath("$._embedded.resources", hasSize(2)))
            .andExpect(jsonPath("$._embedded.resources[*].id", containsInAnyOrder(entityId.getId().intValue(), anotherEntityId.getId().intValue())))
            .andExpect(jsonPath("$._embedded.resources[*].name", containsInAnyOrder(command.name, anotherCommand.name)))
            .andExpect(jsonPath("$._embedded.resources[*]._links", everyItem(hasKey("self"))));

    }

}