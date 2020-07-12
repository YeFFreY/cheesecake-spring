package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.yeffrey.cheesecakespring.infrastructure.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.endpoints.LibrariesEndpoint;

import static org.assertj.core.api.Assertions.assertThat;

class LibrariesControllerTest extends RestIntegrationTest implements LibrariesEndpoint {

    @Override
    public MockMvc getMvc() {
        return this.mvc;
    }

    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    @Test
    @WithMockUser
    void userCanCreateHisOwnLibrary() throws Exception {
        EntityId entityId = userLibrary();
        assertThat(entityId).isNotNull();
    }

    @Test
    @WithMockUser
    void userCanCreateOnlyOneLibrary() throws Exception {
        EntityId entityId = userLibrary();
        assertThat(entityId).isNotNull();
        EntityId anotherAttempt = userLibrary();
        assertThat(anotherAttempt).isEqualTo(entityId);
    }
}
