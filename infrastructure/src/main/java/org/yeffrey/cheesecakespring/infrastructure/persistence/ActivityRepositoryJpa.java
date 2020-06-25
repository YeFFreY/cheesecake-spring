package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.activities.Activity;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

interface ActivityRepositoryJpa extends Repository<Activity, Long> {
    Activity save(Activity entity);

    Optional<ActivityDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId);

    Optional<Activity> findByIdAndOwnerId(long id, UserId ownerId);

    List<ActivityOverview> findAllByOwnerId(UserId ownerId);
}
