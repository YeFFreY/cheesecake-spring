package org.yeffrey.cheesecakespring.activities.dto;

import org.springframework.hateoas.server.core.Relation;
import org.yeffrey.cheesecakespring.activities.domain.ActivityName;

/**
 * Light represenation of an activity when activities are retrieved as a list.
 * This needs to be a class to benefit from hateoas using @{@link Relation}
 */
@Relation(collectionRelation = "activities")
public class ActivityOverview implements ModelDto {
    private final Long id;
    private final String name;

    public ActivityOverview(Long id, ActivityName name) {
        this.id = id;
        this.name = name.asString();
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
