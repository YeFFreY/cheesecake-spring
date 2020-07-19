package org.yeffrey.cheesecakespring.infrastructure.web.rest.index;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yeffrey.cheesecakespring.infrastructure.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.utils.JsonTestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiIndexControllerTest extends RestIntegrationTest {

    @Test
    @WithMockUser
    void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api")
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(JsonTestUtils.hasLinksCount(1))
            .andExpect(JsonTestUtils.hasLink("activities"));
    }

}