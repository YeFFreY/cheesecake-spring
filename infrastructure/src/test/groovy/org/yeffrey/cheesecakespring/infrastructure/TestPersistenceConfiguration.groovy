package org.yeffrey.cheesecakespring.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.yeffrey.cheesecakespring.common.domain.UserId

@Profile("repository-only")
@Configuration
@EnableJpaAuditing
class TestPersistenceConfiguration {
    @Bean
    AuditorAware<UserId> auditorProvider() {
        return () -> Optional.of(UserId.from("user"))
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new NoOpPasswordEncoder()
    }

}
