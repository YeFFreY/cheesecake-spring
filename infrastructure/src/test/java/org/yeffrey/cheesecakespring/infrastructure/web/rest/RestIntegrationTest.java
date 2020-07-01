package org.yeffrey.cheesecakespring.infrastructure.web.rest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@Sql(value = "classpath:db/migration/afterMigrate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // One way to reset db between test, but slow, faster one is using @Sql
// Not transactional, because flush is not possible between actions, so reset database with migration "afterMigrate.sql"
public abstract class RestIntegrationTest {

}
