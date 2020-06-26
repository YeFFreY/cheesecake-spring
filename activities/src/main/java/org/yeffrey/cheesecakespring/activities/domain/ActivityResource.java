package org.yeffrey.cheesecakespring.activities.domain;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import javax.validation.constraints.Min;

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

    private ActivityResource(Activity activity, Resource resource, int quantity) {
        this.activity = activity;
        this.resource = resource;
        this.quantity = quantity;
        this.id = new ActivityResourceId(activity.getId(), resource.getId());
    }

    public static ActivityResource from(Activity activity, Resource resource, int quantity) {
        Preconditions.checkNotNull(activity);
        Preconditions.checkNotNull(resource);
        Preconditions.checkArgument(quantity > 0);
        return new ActivityResource(activity, resource, quantity);
    }

    public Activity getActivity() {
        return activity;
    }

    public Resource getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }
}
