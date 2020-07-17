package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails;

import java.util.List;
import java.util.Optional;

public interface ActivityRepositoryPort {
    Activity save(Activity activity);

    boolean activityBelongsToUserLibrary(Long id,
                                         UserId userId);

    List<ActivityResourceDetails> findActivityResources(Long id);

    Optional<ActivityDetails> findDetailsById(Long id);

    List<ActivityOverview> findActivitiesByLibrary(Library library);

    Optional<Activity> findById(Long activityId);
}
