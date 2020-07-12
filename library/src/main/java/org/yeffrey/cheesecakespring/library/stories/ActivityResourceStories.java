package org.yeffrey.cheesecakespring.library.stories;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.library.core.ResourceNotFoundException;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.domain.Resource;
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.library.ports.ActivityRepositoryPort;
import org.yeffrey.cheesecakespring.library.ports.ResourceRepositoryPort;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ActivityResourceStories {
    private final ActivityRepositoryPort activityRepository;
    private final ResourceRepositoryPort resourceRepository;

    public ActivityResourceStories(ActivityRepositoryPort activityRepository,
                                   ResourceRepositoryPort resourceRepository) {
        this.activityRepository = activityRepository;
        this.resourceRepository = resourceRepository;
    }

    @PreAuthorize("@policy.canManageActivity(authentication, #activityId)")
    public List<ActivityResourceDetails> findActivityResources(Long activityId) {
        return this.activityRepository.findActivityResources(activityId);
    }

    @PreAuthorize("@policy.canManageActivity(authentication, #activityId) && @policy.canManageResource(authentication, #command.resourceId)")
    @Transactional
    public boolean addResourceToActivity(Long activityId,
                                         AddResourceToActivityCommand command) {
        Activity activity = this.activityRepository.findById(activityId).orElseThrow(ResourceNotFoundException::new);
        Resource resource = this.resourceRepository.findById(command.resourceId).orElseThrow(ResourceNotFoundException::new);
        boolean added = activity.addResource(resource, command.quantity);
        this.activityRepository.save(activity);
        return added;
    }

    @PreAuthorize("@policy.canManageActivity(authentication, #activityId) && @policy.canManageResource(authentication, #resourceId)")
    @Transactional
    public boolean removeResourceFromActivity(Long activityId,
                                              Long resourceId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(ResourceNotFoundException::new);
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(ResourceNotFoundException::new);
        boolean removed = activity.removeResource(resource);
        this.activityRepository.save(activity);
        return removed;
    }

}
