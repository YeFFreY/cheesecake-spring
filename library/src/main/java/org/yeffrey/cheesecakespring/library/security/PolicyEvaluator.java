package org.yeffrey.cheesecakespring.library.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.library.domain.UserId;
import org.yeffrey.cheesecakespring.library.ports.ActivityRepositoryPort;
import org.yeffrey.cheesecakespring.library.ports.ResourceRepositoryPort;

@Component("policy")
public class PolicyEvaluator {
    private final ActivityRepositoryPort activityRepositoryPort;
    private final ResourceRepositoryPort resourceRepositoryPort;

    public PolicyEvaluator(ActivityRepositoryPort activityRepositoryPort,
                           ResourceRepositoryPort resourceRepositoryPort) {
        this.activityRepositoryPort = activityRepositoryPort;
        this.resourceRepositoryPort = resourceRepositoryPort;
    }

    public boolean canManageActivity(Authentication authentication,
                                     Long activityId) {
        return this.activityRepositoryPort.activityBelongsToUserLibrary(activityId, UserId.from(authentication.getName()));
    }

    public boolean canManageResource(Authentication authentication,
                                     Long resourceId) {
        return this.resourceRepositoryPort.resourceBelongsToUserLibrary(resourceId, UserId.from(authentication.getName()));
    }

}
