package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

// essayons de ne pas utiliser JpaRepository pour voir si on sait s'en passer et definir uniquement les methodes qu'on a besoin
interface ResourceRepositoryJpa extends Repository<Resource, Long> {
    Resource save(Resource entity);

    Optional<ResourceDetails> findDetailsById(Long id);

    Optional<Resource> findById(long id);

    List<ResourceOverview> findOverviewBy();
}
