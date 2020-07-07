package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.core.RepositoryInMemory;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.ActivityResourceId;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResourceRepositoryInMemory extends RepositoryInMemory<Resource> implements ResourceRepository {

    private static final ResourceRepositoryInMemory INSTANCE = new ResourceRepositoryInMemory();

    private ResourceRepositoryInMemory() {
    }

    public static ResourceRepositoryInMemory instance() {
        return INSTANCE;
    }

    @Override
    public Optional<ResourceDetails> findDetailsById(Long id) {
        return Optional.ofNullable(this.db.get(id))
            .map(r -> new ResourceDetails(r.getId(), r.getName(), r.getDescription(), r.getQuantityUnit()));
    }

    @Override
    public Optional<Resource> findById(long id) {
        return Optional.ofNullable(this.db.get(id));
    }

    @Override
    public List<ResourceOverview> findAll() {
        return this.db.values().stream()
            .map(r -> new ResourceOverview(r.getId(), r.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public List<ActivityResourceDetails> findAllByActivityId(Long activityId) {
        return ActivityRepositoryInMemory.instance().findById(activityId)
            .stream()
            .map(Activity::getResources)
            .flatMap(Collection::stream)
            .map(r -> new ActivityResourceDetails(new ActivityResourceId(activityId, r.getResource().getId()), r.getQuantity()))
            .collect(Collectors.toList());
    }

}