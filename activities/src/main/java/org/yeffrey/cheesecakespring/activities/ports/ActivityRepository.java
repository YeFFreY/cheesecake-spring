package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository {
    Activity save(Activity entity);

    Optional<ActivityDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId);

    Optional<Activity> findByIdAndOwnerId(long id, UserId ownerId);

    List<ActivityOverview> findAllByOwnerId(UserId ownerId);

    boolean existsByIdAndOwnerId(Long id, UserId ownerId);
}