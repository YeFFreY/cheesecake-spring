package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.core.RepositoryInMemory;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActivityRepositoryInMemory extends RepositoryInMemory<Activity> implements ActivityRepository {

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
