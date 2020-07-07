package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.activities.domain.ActivityResource;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;

import java.util.List;

// essayons de ne pas utiliser JpaRepository pour voir si on sait s'en passer et definir uniquement les methodes qu'on a besoin
interface ActivityResourceRepositoryJpa extends Repository<ActivityResource, Long> {

    List<ActivityResourceDetails> findAllByActivityId(Long activityId);
}
