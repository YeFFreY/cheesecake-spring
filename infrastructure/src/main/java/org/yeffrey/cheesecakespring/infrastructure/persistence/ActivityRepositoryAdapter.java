package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.library.ports.ActivityRepositoryPort;

import java.util.List;
import java.util.Optional;

@PortAdapter
@Component
public class ActivityRepositoryAdapter implements ActivityRepositoryPort {
    private final ActivityRepositoryJpa activityRepositoryJpa;
    private final ActivityResourceRepositoryJpa activityResourceRepositoryJpa;

    public ActivityRepositoryAdapter(ActivityRepositoryJpa activityRepositoryJpa,
                                     ActivityResourceRepositoryJpa activityResourceRepositoryJpa) {
        this.activityRepositoryJpa = activityRepositoryJpa;
        this.activityResourceRepositoryJpa = activityResourceRepositoryJpa;
    }

    @Override
    public Activity save(Activity entity) {
        return this.activityRepositoryJpa.save(entity);
    }

    @Override
    public boolean activityBelongsToUserLibrary(Long activityId,
                                                UserId userId) {
        return this.activityRepositoryJpa.activityBelongsToUserLibrary(activityId, userId);
    }

    // TODO should this one be moved to ResourceRepositoryAdapter ?
    @Override
    public List<ActivityResourceDetails> findActivityResources(Long activityId) {
        return activityResourceRepositoryJpa.findAllByActivityId(activityId);
    }

    @Override
    public Optional<ActivityDetails> findDetailsById(Long id) {
        return this.activityRepositoryJpa.findDetailsById(id);
    }

    @Override
    public List<ActivityOverview> findActivitiesByLibrary(Library library) {
        return this.activityRepositoryJpa.findOverviewByLibrary(library);
    }

    @Override
    public Optional<Activity> findById(Long activityId) {
        return this.activityRepositoryJpa.findById(activityId);
    }
}
