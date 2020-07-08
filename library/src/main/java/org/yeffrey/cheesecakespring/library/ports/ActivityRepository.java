package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository {
    Activity save(Activity entity);

    Optional<ActivityDetails> findDetailsById(Long id);

    Optional<Activity> findById(long id);

    // TODO still need to find a way to retrieve only the one he has access because filter by owner id is not enough anymore (use might get access indirectly to an activity f.i. when another user allows him)
    List<ActivityOverview> findAll();

    boolean existsById(Long id);
}