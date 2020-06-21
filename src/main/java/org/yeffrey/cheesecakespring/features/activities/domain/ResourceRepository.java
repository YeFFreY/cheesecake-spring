package org.yeffrey.cheesecakespring.features.activities.domain;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

// essayons de ne pas utiliser JpaRepository pour voir si on sait s'en passer et definir uniquement les methodes qu'on a besoin
interface ResourceRepository extends Repository<Resource, Long> {
    Resource save(Resource entity);

    Optional<ResourceDetails> findDetailsByIdAndOwnerId(Long id, String ownerId);

    Optional<Resource> findByIdAndOwnerId(long id, String ownerId);

    List<ResourceOverview> findAllByOwnerId(String ownerId);
}
