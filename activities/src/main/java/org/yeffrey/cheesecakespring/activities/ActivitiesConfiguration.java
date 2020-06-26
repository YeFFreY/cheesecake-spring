package org.yeffrey.cheesecakespring.activities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yeffrey.cheesecakespring.activities.ports.*;

@Configuration
class ActivitiesConfiguration {
    ActivityStories activityStories(AuthenticatedUserService authenticatedUserService) {
        return activityStories(new ActivityRepositoryInMemory(), authenticatedUserService);
    }

    @Bean
    ActivityStories activityStories(ActivityRepository activityRepository, AuthenticatedUserService authenticatedUserService) {
        return new ActivityStories(
            activityRepository,
            authenticatedUserService);
    }

    ResourceStories resourceStories(AuthenticatedUserService authenticatedUserService) {
        return resourceStories(new ResourceRepositoryInMemory(), authenticatedUserService);
    }

    @Bean
    ResourceStories resourceStories(ResourceRepository resourceRepository, AuthenticatedUserService authenticatedUserService) {
        return new ResourceStories(
            resourceRepository,
            authenticatedUserService);
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
