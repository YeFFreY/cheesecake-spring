package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ResourceRepositoryAdapter implements ResourceRepository {
    private final ResourceRepositoryJpa resourceRepository;
    private final ActivityResourceRepositoryJpa activityResourceRepository;

    public ResourceRepositoryAdapter(ResourceRepositoryJpa resourceRepository, ActivityResourceRepositoryJpa activityResourceRepository) {
        this.resourceRepository = resourceRepository;
        this.activityResourceRepository = activityResourceRepository;
    }

    @Override
    public Resource save(Resource entity) {
        return this.resourceRepository.save(entity);
    }

    @Override
    public Optional<ResourceDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId) {
        return this.resourceRepository.findDetailsByIdAndOwnerId(id, ownerId);
    }

    @Override
    public Optional<Resource> findByIdAndOwnerId(long id, UserId ownerId) {
        return this.resourceRepository.findByIdAndOwnerId(id, ownerId);
    }

    @Override
    public List<ResourceOverview> findAllByOwnerId(UserId ownerId) {
        return this.resourceRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public List<ActivityResourceDetails> findAllByActivityIdAndOwnerId(Long activityId, UserId userId) {
        return activityResourceRepository.findAllByActivityIdAndActivityOwnerId(activityId, userId);
    }
}
