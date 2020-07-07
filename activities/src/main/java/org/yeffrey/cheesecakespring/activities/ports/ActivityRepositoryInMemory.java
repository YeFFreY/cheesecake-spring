package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.core.RepositoryInMemory;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActivityRepositoryInMemory extends RepositoryInMemory<Activity> implements ActivityRepository {

    private static final ActivityRepositoryInMemory INSTANCE = new ActivityRepositoryInMemory();

    private ActivityRepositoryInMemory() {
    }

    public static ActivityRepositoryInMemory instance() {
        return INSTANCE;
    }

    @Override
    public Optional<ActivityDetails> findDetailsById(Long id) {
        return Optional.ofNullable(this.db.get(id))
            .map(a -> new ActivityDetails(a.getId(), a.getName(), a.getDescription()));
    }

    @Override
    public Optional<Activity> findById(long id) {
        return Optional.ofNullable(this.db.get(id));
    }

    @Override
    public List<ActivityOverview> findAll() {
        return this.db.values().stream()
            .map(a -> new ActivityOverview(a.getId(), a.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        Activity activity = this.db.get(id);
        return activity != null;
    }

}
