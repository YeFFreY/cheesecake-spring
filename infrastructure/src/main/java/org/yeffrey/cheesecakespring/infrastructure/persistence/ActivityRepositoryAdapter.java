package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.library.ports.ActivityRepository;

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
    public Optional<ActivityDetails> findDetailsById(Long id) {
        return this.activityRepositoryJpa.findDetailsById(id);
    }

    @Override
    public Optional<Activity> findById(long id) {
        return this.activityRepositoryJpa.findById(id);
    }

    @Override
    public List<ActivityOverview> findAll() {
        return this.activityRepositoryJpa.findOverviewBy();
    }

    @Override
    public boolean existsById(Long id) {
        return this.activityRepositoryJpa.existsById(id);
    }
}
