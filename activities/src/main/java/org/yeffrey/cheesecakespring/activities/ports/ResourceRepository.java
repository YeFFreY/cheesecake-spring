package org.yeffrey.cheesecakespring.activities.ports;

import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    Resource save(Resource entity);

    Optional<ResourceDetails> findDetailsById(Long id);

    Optional<Resource> findById(long id);

    List<ResourceOverview> findAll();

    List<ActivityResourceDetails> findAllByActivityId(Long activityId);
}
