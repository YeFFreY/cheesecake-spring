package org.yeffrey.cheesecakespring.activities.domain;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "activity_resources")
public class ActivityResource {
    @EmbeddedId
    private ActivityResourceId id = new ActivityResourceId();

    @ManyToOne
    @MapsId("activityId")
    private Activity activity;
    @ManyToOne
    @MapsId("resourceId")
    private Resource resource;

    @Min(1)
    private int quantity;

    protected ActivityResource() {
    }

    private ActivityResource(Activity activity, Resource resource) {
        this.activity = activity;
        this.resource = resource;
        this.quantity = 1;
        this.id = new ActivityResourceId(activity.getId(), resource.getId());
    }

    public static ActivityResource from(Activity activity, Resource resource, int quantity) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(resource);
        final ActivityResource activityResource = new ActivityResource(activity, resource);
        activityResource.setQuantity(quantity);
        return activityResource;
    }

    protected Activity getActivity() {
        return activity;
    }

    public Resource getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }

    void setQuantity(int quantity) {
        Preconditions.checkArgument(quantity > 0);
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityResource)) return false;
        ActivityResource that = (ActivityResource) o;
        return activity.equals(that.activity) &&
            resource.equals(that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, resource);
    }
}
