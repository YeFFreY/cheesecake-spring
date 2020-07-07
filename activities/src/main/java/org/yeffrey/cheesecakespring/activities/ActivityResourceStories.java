package org.yeffrey.cheesecakespring.activities;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.activities.core.ResourceNotFoundException;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.activities.dto.AdjustActivityResourceQuantityCommand;
import org.yeffrey.cheesecakespring.activities.ports.ActivityRepository;
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ActivityResourceStories {
    private final ActivityRepository activityRepository;
    private final ResourceRepository resourceRepository;

    public ActivityResourceStories(ActivityRepository activityRepository, ResourceRepository resourceRepository) {
        this.activityRepository = activityRepository;
        this.resourceRepository = resourceRepository;
    }

    @Transactional
    public boolean activityRequiresResource(Long activityId, AddResourceToActivityCommand command) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(ResourceNotFoundException::new);
        Resource resource = resourceRepository.findById(command.resourceId).orElseThrow(ResourceNotFoundException::new);
        return activity.addResource(resource, command.quantity);
    }

    public List<ActivityResourceDetails> findActivityResources(Long activityId) {
        if (this.activityRepository.existsById(activityId)) {
            return this.resourceRepository.findAllByActivityId(activityId);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional
    public boolean resourceNotRequiredAnymore(Long activityId, Long resourceId) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(ResourceNotFoundException::new);
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(ResourceNotFoundException::new);
        return activity.removeResource(resource);
    }

    @Transactional
    public boolean adjustActivityResourceQuantity(Long activityId, Long resourceId, AdjustActivityResourceQuantityCommand command) {
        Activity activity = activityRepository.findById(activityId).orElseThrow(ResourceNotFoundException::new);
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(ResourceNotFoundException::new);
        return activity.updateResource(resource, command.quantity);
    }
}
