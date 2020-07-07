package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
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
    public Optional<ResourceDetails> findDetailsById(Long id) {
        return this.resourceRepository.findDetailsById(id);
    }

    @Override
    public Optional<Resource> findById(long id) {
        return this.resourceRepository.findById(id);
    }

    @Override
    public List<ResourceOverview> findAll() {
        return this.resourceRepository.findOverviewBy();
    }

    @Override
    public List<ActivityResourceDetails> findAllByActivityId(Long activityId) {
        return activityResourceRepository.findAllByActivityId(activityId);
    }
}
