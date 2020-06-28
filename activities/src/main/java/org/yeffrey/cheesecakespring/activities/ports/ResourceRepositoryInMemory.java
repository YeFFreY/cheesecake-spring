package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.core.RepositoryInMemory;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.ActivityResourceId;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
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
    public Optional<ResourceDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(r -> r.belongsTo(ownerId))
            .map(r -> new ResourceDetails(r.getId(), r.getName(), r.getDescription(), r.getQuantityUnit()));
    }

    @Override
    public Optional<Resource> findByIdAndOwnerId(long id, UserId ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(r -> r.belongsTo(ownerId));
    }

    @Override
    public List<ResourceOverview> findAllByOwnerId(UserId ownerId) {
        return this.db.values().stream()
            .filter(r -> r.belongsTo(ownerId))
            .map(r -> new ResourceOverview(r.getId(), r.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public List<ActivityResourceDetails> findAllByActivityIdAndOwnerId(Long activityId, UserId userId) {
        return ActivityRepositoryInMemory.instance().findByIdAndOwnerId(activityId, userId)
            .stream()
            .map(Activity::getResources)
            .flatMap(Collection::stream)
            .map(r -> new ActivityResourceDetails(new ActivityResourceId(activityId, r.getResource().getId()), r.getQuantity()))
            .collect(Collectors.toList());
    }

}