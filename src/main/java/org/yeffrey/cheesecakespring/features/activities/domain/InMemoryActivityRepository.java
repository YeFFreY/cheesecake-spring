package org.yeffrey.cheesecakespring.features.activities.domain;

import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

class InMemoryActivityRepository implements ActivityRepository {
    private final ConcurrentHashMap<Long, Activity> db = new ConcurrentHashMap<>();
    private final AtomicLong activitySequence = new AtomicLong();

    @Override
    public Activity save(Activity entity) {
        if(entity.getId() == null) {
            entity.setId(activitySequence.getAndIncrement());
        }
        db.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<ActivityDetails> findDetailsByIdAndOwnerId(Long id, String ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(a -> a.belongsTo(ownerId))
            .map(ActivityDetailsDto::new);
    }

    @Override
    public Optional<Activity> findByIdAndOwnerId(long id, String ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(a -> a.belongsTo(ownerId));
    }

    @Override
    public List<ActivityOverview> findAllByOwnerId(String ownerId) {
        return this.db.values().stream()
            .filter(a -> a.belongsTo(ownerId))
            .map(ActivityOverviewDto::new)
            .collect(Collectors.toList());
    }

    static class ActivityDetailsDto implements ActivityDetails {

        private final Activity activity;
        public ActivityDetailsDto(Activity activity) {
            this.activity = activity;
        }

        @Override
        public Long getId() {
            return activity.getId();
        }

        @Override
        public String getName() {
            return activity.getName();
        }

        @Override
        public String getDescription() {
            return activity.getDescription();
        }
    }

    static class ActivityOverviewDto implements ActivityOverview {
        private final Activity activity;
        public ActivityOverviewDto(Activity activity) {
            this.activity = activity;
        }

        @Override
        public Long getId() {
            return activity.getId();
        }

        @Override
        public String getName() {
            return activity.getName();
        }

    }
}
