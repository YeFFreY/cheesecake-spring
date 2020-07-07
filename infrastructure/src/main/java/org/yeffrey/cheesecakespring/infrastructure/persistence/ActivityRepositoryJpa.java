package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

interface ActivityRepositoryJpa extends Repository<Activity, Long> {
    Activity save(Activity entity);

    Optional<ActivityDetails> findDetailsById(Long id);

    Optional<Activity> findById(long id);

    List<ActivityOverview> findOverviewBy();

    boolean existsById(Long id);
}
