package org.yeffrey.cheesecakespring.activities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yeffrey.cheesecakespring.activities.ports.*;

@Configuration
class ActivitiesConfiguration {
    ActivityStories activityStories(AuthenticatedUserService authenticatedUserService) {
        return activityStories(ActivityRepositoryInMemory.instance(), authenticatedUserService);
    }

    @Bean
    ActivityStories activityStories(ActivityRepository activityRepository, AuthenticatedUserService authenticatedUserService) {
        return new ActivityStories(
            activityRepository,
            authenticatedUserService);
    }

    ResourceStories resourceStories(AuthenticatedUserService authenticatedUserService) {
        return resourceStories(ResourceRepositoryInMemory.instance(), authenticatedUserService);
    }

    @Bean
    ResourceStories resourceStories(ResourceRepository resourceRepository, AuthenticatedUserService authenticatedUserService) {
        return new ResourceStories(
            resourceRepository,
            authenticatedUserService);
    }

    ActivityResourceStories activityResourceStories(AuthenticatedUserService authenticatedUserService) {
        return new ActivityResourceStories(ActivityRepositoryInMemory.instance(), ResourceRepositoryInMemory.instance(), authenticatedUserService);
    }

    @Bean
    ActivityResourceStories activityResourceStories(ActivityRepository activityRepository, ResourceRepository resourceRepository, AuthenticatedUserService authenticatedUserService) {
        return new ActivityResourceStories(activityRepository, resourceRepository, authenticatedUserService);
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
