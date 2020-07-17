package org.yeffrey.cheesecakespring.infrastructure.persistence

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.yeffrey.cheesecakespring.infrastructure.TestPersistenceConfiguration
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter
import spock.lang.Shared
import spock.lang.Specification

@ActiveProfiles(["integration", "repository-only"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = [PortAdapter.class]))
// because the classes annotated with Repository are not the jpa interface...
@Import(TestPersistenceConfiguration.class)
class IntegrationSpecification extends Specification {
    @Shared
    def faker = new Faker()

    @Autowired
    private TestEntityManager entityManager

    protected void flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }
}
