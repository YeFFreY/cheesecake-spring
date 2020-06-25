package org.yeffrey.cheesecakespring.activities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ActivitiesConfiguration {
    ActivityStories activityStories(AuthenticatedUserPort authenticatedUserPort) {
        return activityStories(new ActivityRepositoryInMemory(), authenticatedUserPort);
    }

    @Bean
    ActivityStories activityStories(ActivityRepository activityRepository, AuthenticatedUserPort authenticatedUserPort) {
        return new ActivityStories(
            activityRepository,
            authenticatedUserPort);
    }

    ResourceStories resourceStories(AuthenticatedUserPort authenticatedUserPort) {
        return resourceStories(new ResourceRepositoryInMemory(), authenticatedUserPort);
    }

    @Bean
    ResourceStories resourceStories(ResourceRepository resourceRepository, AuthenticatedUserPort authenticatedUserPort) {
        return new ResourceStories(
            resourceRepository,
            authenticatedUserPort);
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
