package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.repository.Repository;
import org.yeffrey.cheesecakespring.activities.Resource;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

// essayons de ne pas utiliser JpaRepository pour voir si on sait s'en passer et definir uniquement les methodes qu'on a besoin
interface ResourceRepositoryJpa extends Repository<Resource, Long> {
    Resource save(Resource entity);

    Optional<ResourceDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId);

    Optional<Resource> findByIdAndOwnerId(long id, UserId ownerId);

    List<ResourceOverview> findAllByOwnerId(UserId ownerId);
}
