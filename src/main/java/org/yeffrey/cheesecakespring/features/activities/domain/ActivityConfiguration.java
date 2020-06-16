package org.yeffrey.cheesecakespring.features.activities.domain;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yeffrey.cheesecakespring.features.common.AuthenticatedUserPort;

@Configuration
class ActivityConfiguration {
    ActivityStories activityStories(AuthenticatedUserPort authenticatedUserPort) {
        return activityStories(new InMemoryActivityRepository(), authenticatedUserPort);
    }

    @Bean
    ActivityStories activityStories(ActivityRepository activityRepository, AuthenticatedUserPort authenticatedUserPort) {
        return new ActivityStories(
            activityRepository,
            authenticatedUserPort);
    }
}
