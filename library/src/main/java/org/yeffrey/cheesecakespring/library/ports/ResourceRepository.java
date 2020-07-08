package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.library.domain.Resource;
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    Resource save(Resource entity);

    Optional<ResourceDetails> findDetailsById(Long id);

    Optional<Resource> findById(long id);

    List<ResourceOverview> findAll();

    List<ActivityResourceDetails> findAllByActivityId(Long activityId);
}
