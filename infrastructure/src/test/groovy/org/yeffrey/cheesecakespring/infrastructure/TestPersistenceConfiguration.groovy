package org.yeffrey.cheesecakespring.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.yeffrey.cheesecakespring.library.domain.UserId

@Profile("repository-only")
@Configuration
@EnableJpaAuditing
class TestPersistenceConfiguration {
    @Bean
    AuditorAware<UserId> auditorProvider() {
        return () -> Optional.of(UserId.from("user"))
    }

}
