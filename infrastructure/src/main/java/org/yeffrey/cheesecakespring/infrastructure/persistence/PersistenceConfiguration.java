package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan("org.yeffrey.cheesecakespring.infrastructure.persistence.converters")
// @EntityScan is only used to discover the @Converter
public class PersistenceConfiguration {
}
