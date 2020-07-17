package org.yeffrey.cheesecakespring.library;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserPort;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EntityScan
class LibraryConfiguration {

    @Bean
    public AuditorAware<UserId> auditorProvider(AuthenticatedUserPort authenticatedUserPort) {
        return authenticatedUserPort::findAuthenticatedUserId;
    }

    // https://www.baeldung.com/spring-enum-request-param
/*
    @Bean
    Converter<String, ResourceQuantityUnit> resourceQuantityUnitConverter() {

        return source -> {
            try {
                return ResourceQuantityUnit.valueOf(source.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        };
    }
*/

}
