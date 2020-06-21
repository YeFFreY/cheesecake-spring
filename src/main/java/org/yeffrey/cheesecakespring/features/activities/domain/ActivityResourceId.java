package org.yeffrey.cheesecakespring.features.activities.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class ActivityResourceId implements Serializable {

    @Column(name = "activity_id")
    private Long activityId;
    @Column(name = "resource_id")
    private Long resourceId;

    protected ActivityResourceId() {
    }

    public ActivityResourceId(Long activityId, Long resourceId) {
        this.activityId = activityId;
        this.resourceId = resourceId;
    }


    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityResourceId that = (ActivityResourceId) o;
        return activityId.equals(that.activityId) &&
            resourceId.equals(that.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, resourceId);
    }
}
