package org.yeffrey.cheesecakespring.features.activities.domain;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

// essayons de ne pas utiliser JpaRepository pour voir si on sait s'en passer et definir uniquement les methodes qu'on a besoin
interface ActivityRepository extends Repository<Activity, Long> {
    Activity save(Activity entity);
    Optional<ActivityDetails> findDetailsByIdAndOwnerId(Long id, String ownerId);

    Optional<Activity> findByIdAndOwnerId(long id, String ownerId);

    List<ActivityOverview> findAllByOwnerId(String ownerId);
}
