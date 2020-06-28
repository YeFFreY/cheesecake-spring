package org.yeffrey.cheesecakespring.activities;

import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.activities.core.AccessDeniedException;
import org.yeffrey.cheesecakespring.activities.core.ResourceNotFoundException;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.activities.ports.ActivityRepository;
import org.yeffrey.cheesecakespring.activities.ports.AuthenticatedUserService;
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository;

import java.util.List;

@Transactional(readOnly = true)
public class ActivityResourceStories {
    private final ActivityRepository activityRepository;
    private final ResourceRepository resourceRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ActivityResourceStories(ActivityRepository activityRepository, ResourceRepository resourceRepository, AuthenticatedUserService authenticatedUserService) {
        this.activityRepository = activityRepository;
        this.resourceRepository = resourceRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    public boolean activityRequiresResource(Long activityId, AddResourceToActivityCommand command) {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        Activity activity = activityRepository.findByIdAndOwnerId(activityId, userId).orElseThrow(ResourceNotFoundException::new);
        Resource resource = resourceRepository.findByIdAndOwnerId(command.resourceId, userId).orElseThrow(ResourceNotFoundException::new);
        return activity.addResource(resource, command.quantity);
    }

    public List<ActivityResourceDetails> findActivityResources(Long activityId) {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        if (this.activityRepository.existsByIdAndOwnerId(activityId, userId)) {
            return this.resourceRepository.findAllByActivityIdAndOwnerId(activityId, userId);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
