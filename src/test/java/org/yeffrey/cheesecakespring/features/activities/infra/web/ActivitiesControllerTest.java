package org.yeffrey.cheesecakespring.features.activities.infra.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.features.common.EntityId;

import javax.transaction.Transactional;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Transactional
class ActivitiesControllerTest {
    Faker faker = new Faker();

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    private CreateUpdateActivityCommand givenACreateUpdateCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph());
    }

    protected EntityId newActivity(CreateUpdateActivityCommand command, String userId) throws Exception {
        MockHttpServletRequestBuilder post = post("/api/activities");

        if (Objects.nonNull(userId)) {
          post = post.with(user(userId));
        }

        String response = mvc.perform(post
            .content(mapper.writeValueAsString(command))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

        return mapper.readValue(response, EntityId.class);
    }

    protected EntityId newActivity(CreateUpdateActivityCommand command) throws Exception {
        return this.newActivity(command, null);
    }

    protected ResultActions showActivity(EntityId entityId) throws Exception {
        return mvc.perform(get("/api/activities/{id}", entityId.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    }

    protected ResultActions showActivities() throws Exception {
        return mvc.perform(get("/api/activities")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    private void updateActivity(EntityId entityId, CreateUpdateActivityCommand command) throws Exception {
        mvc.perform(put("/api/activities/{id}", entityId.getId())
            .content(mapper.writeValueAsString(command))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    public void userShouldRetrieveAnActivityHeCreated() throws Exception {
        CreateUpdateActivityCommand command = givenACreateUpdateCommand();
        EntityId entityId = newActivity(command);
        assertThat(entityId).isNotNull();

        showActivity(entityId)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(entityId.getId()))
            .andExpect(jsonPath("$.name").value(command.name))
            .andExpect(jsonPath("$.description").value(command.description));

    }

    @Test
    @WithMockUser
    public void userShouldNotRetrieveAnActivityCreatedByAnotherUser() throws Exception {
        EntityId anotherUserActivity = newActivity(givenACreateUpdateCommand(), "anotherUser");

        showActivity(anotherUserActivity).andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());

    }

    @Test
    @WithMockUser
    public void userShouldUpdateAnActivityHeCreated() throws Exception {
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
    public void userShouldRetrieveAllActivitiesHeCreated() throws Exception {
        CreateUpdateActivityCommand command = givenACreateUpdateCommand();
        EntityId entityId = newActivity(command);
        CreateUpdateActivityCommand anotherCommand = givenACreateUpdateCommand();
        EntityId anotherEntityId = newActivity(anotherCommand);


        showActivities()
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*].id", containsInAnyOrder(entityId.getId().intValue(), anotherEntityId.getId().intValue())))
            .andExpect(jsonPath("$[*].name", containsInAnyOrder(command.name, anotherCommand.name)));

    }

}