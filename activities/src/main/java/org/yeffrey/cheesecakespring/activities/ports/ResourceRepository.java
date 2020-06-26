package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    Resource save(Resource entity);

    Optional<ResourceDetails> findDetailsByIdAndOwnerId(Long id, UserId ownerId);

    Optional<Resource> findByIdAndOwnerId(long id, UserId ownerId);

    List<ResourceOverview> findAllByOwnerId(UserId ownerId);
}
