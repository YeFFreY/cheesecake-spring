package org.yeffrey.cheesecakespring.infrastructure.web.rest.index;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.RestIntegrationTest;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.utils.JsonTestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiIndexControllerTest extends RestIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @Test
    public void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(JsonTestUtils.hasLinksCount(1))
            .andExpect(JsonTestUtils.hasLink("activities"));
    }

}