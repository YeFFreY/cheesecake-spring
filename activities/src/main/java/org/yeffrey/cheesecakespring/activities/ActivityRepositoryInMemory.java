package org.yeffrey.cheesecakespring.activities;

import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

class ActivityRepositoryInMemory implements ActivityRepository {
    private final ConcurrentHashMap<Long, Activity> db = new ConcurrentHashMap<>();
    private final AtomicLong activitySequence = new AtomicLong();

    @Override
    public Activity save(Activity entity) {
        if (entity.getId() == null) {
            entity.setId(activitySequence.getAndIncrement());
        }
        db.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<ActivityDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(a -> a.belongsTo(ownerId))
            .map(a -> new ActivityDetails(a.getId(), a.getName(), a.getDescription()));
    }

    @Override
    public Optional<Activity> findByIdAndOwnerId(long id, UserId ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(a -> a.belongsTo(ownerId));
    }

    @Override
    public List<ActivityOverview> findAllByOwnerId(UserId ownerId) {
        return this.db.values().stream()
            .filter(a -> a.belongsTo(ownerId))
            .map(a -> new ActivityOverview(a.getId(), a.getName()))
            .collect(Collectors.toList());
    }


}
