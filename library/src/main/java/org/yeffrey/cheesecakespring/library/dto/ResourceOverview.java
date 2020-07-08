package org.yeffrey.cheesecakespring.library.dto;

import org.springframework.hateoas.server.core.Relation;
import org.yeffrey.cheesecakespring.library.domain.ResourceName;

/**
 * Light representation of an activity when activities are retrieved as a list.
 * This needs to be a class to benefit from hateoas using @{@link Relation}
 */
@Relation(collectionRelation = "resources")
public class ResourceOverview implements ModelDto {
    private final Long id;
    private final String name;

    public ResourceOverview(Long id, ResourceName name) {
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
