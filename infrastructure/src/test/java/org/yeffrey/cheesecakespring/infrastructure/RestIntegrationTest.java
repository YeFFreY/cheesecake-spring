package org.yeffrey.cheesecakespring.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Sql(value = "classpath:db/migration/afterMigrate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // One way to reset db between test, but slow, faster one is using @Sql
// Not transactional, because flush is not possible between actions, so reset database with migration "afterMigrate.sql"
public abstract class RestIntegrationTest {
    protected Faker faker = new Faker();

    protected String aUser = "user";
    protected String anotherUser = "anotherUser";

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

}
