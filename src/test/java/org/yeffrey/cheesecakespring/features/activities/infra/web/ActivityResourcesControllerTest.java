package org.yeffrey.cheesecakespring.features.activities.infra.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.features.common.EntityId;
import org.yeffrey.cheesecakespring.utils.JsonTestUtils;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Transactional
class ActivityResourcesControllerTest implements ActivitiesEndpoint {
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

    private CreateUpdateActivityCommand givenACreateUpdateCommand() {
        return new CreateUpdateActivityCommand(faker.lorem().sentence(), faker.lorem().paragraph());
    }

    @Test
    @WithMockUser
    public void userCanAddResourceToExistingActivity() throws Exception {
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


}