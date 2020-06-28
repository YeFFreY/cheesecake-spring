package org.yeffrey.cheesecakespring.activities.dto;

import org.springframework.hateoas.server.core.Relation;
import org.yeffrey.cheesecakespring.activities.domain.ActivityResourceId;

@Relation(collectionRelation = "resources")
public class ActivityResourceDetails {
    private final long id;
    private final int quantity;

    public ActivityResourceDetails(ActivityResourceId id, int quantity) {
        this.id = id.getResourceId();
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
