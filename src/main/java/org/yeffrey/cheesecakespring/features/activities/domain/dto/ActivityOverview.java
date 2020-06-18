package org.yeffrey.cheesecakespring.features.activities.domain.dto;

import org.springframework.hateoas.server.core.Relation;

/**
 * Light represenation of an activity when activities are retrieved as a list.
 * This needs to be a class to benefit from hateoas using @{@link Relation}
 */
@Relation(collectionRelation = "activities")
public class ActivityOverview implements ActivityDto {
    private final Long id;
    private final String name;

    public ActivityOverview(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
