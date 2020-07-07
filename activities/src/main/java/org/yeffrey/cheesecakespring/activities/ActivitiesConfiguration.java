package org.yeffrey.cheesecakespring.activities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.ports.*;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class ActivitiesConfiguration {

    @Bean
    public AuditorAware<UserId> auditorProvider(AuthenticatedUserService authenticatedUserService) {
        return authenticatedUserService::getAuthenticatedUserId;
    }

    ActivityStories activityStories() {
        return activityStories(ActivityRepositoryInMemory.instance());
    }

    private ActivityStories activityStories(ActivityRepository activityRepository) {
        return new ActivityStories(activityRepository);
    }

    ResourceStories resourceStories() {
        return resourceStories(ResourceRepositoryInMemory.instance());
    }

    private ResourceStories resourceStories(ResourceRepository resourceRepository) {
        return new ResourceStories(resourceRepository);
    }

    ActivityResourceStories activityResourceStories() {
        return new ActivityResourceStories(ActivityRepositoryInMemory.instance(), ResourceRepositoryInMemory.instance());
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
