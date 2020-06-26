package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.activities.ports.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActivityRepositoryAdapter implements ActivityRepository {
    private final ActivityRepositoryJpa activityRepositoryJpa;

    public ActivityRepositoryAdapter(ActivityRepositoryJpa activityRepositoryJpa) {
        this.activityRepositoryJpa = activityRepositoryJpa;
    }

    @Override
    public Activity save(Activity entity) {
        return this.activityRepositoryJpa.save(entity);
    }

    @Override
    public Optional<ActivityDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId) {
        return this.activityRepositoryJpa.findDetailsByIdAndOwnerId(id, ownerId);
    }

    @Override
    public Optional<Activity> findByIdAndOwnerId(long id, UserId ownerId) {
        return this.activityRepositoryJpa.findByIdAndOwnerId(id, ownerId);
    }

    @Override
    public List<ActivityOverview> findAllByOwnerId(UserId ownerId) {
        return this.activityRepositoryJpa.findAllByOwnerId(ownerId);
    }
}
