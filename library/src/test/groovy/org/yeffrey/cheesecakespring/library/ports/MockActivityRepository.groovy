package org.yeffrey.cheesecakespring.library.ports

import org.yeffrey.cheesecakespring.library.domain.Activity
import org.yeffrey.cheesecakespring.library.domain.Library
import org.yeffrey.cheesecakespring.library.domain.UserId
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails

import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Collectors

class MockActivityRepository implements ActivityRepositoryPort {
    AtomicLong seq = new AtomicLong()
    Map<Long, Activity> db = [:]

    @Override
    Activity save(Activity activity) {
        if (activity.id == null) {
            activity.id = seq.getAndIncrement()
        }
        db[activity.id] = activity
        return activity
    }

    @Override
    boolean activityBelongsToUserLibrary(Long id, UserId userId) {
        return false
    }

    @Override
    List<ActivityResourceDetails> findActivityResources(Long id) {
        return db.find { it.value.id == id }.value.resources.stream().map(r -> new ActivityResourceDetails(r.id, r.quantity)).collect(Collectors.toList())
    }

    @Override
    Optional<ActivityDetails> findDetailsById(Long id) {
        return this.findById(id)
                .map(a -> new ActivityDetails(a.id, a.name, a.description))
    }

    @Override
    List<ActivityOverview> findActivitiesByLibrary(Library library) {
        return db.findAll { it.value.library == library }.values().stream().map(a -> new ActivityOverview(a.id, a.name)).collect(Collectors.toList())
    }

    @Override
    Optional<Activity> findById(Long activityId) {
        return Optional.ofNullable(db[activityId])
    }
}
