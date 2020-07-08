package org.yeffrey.cheesecakespring.library.dto;

import org.yeffrey.cheesecakespring.library.domain.ActivityDescription;
import org.yeffrey.cheesecakespring.library.domain.ActivityName;

public class ActivityDetails implements ModelDto {
    private final Long id;
    private final String name;
    private final String description;

    public ActivityDetails(Long id, ActivityName name, ActivityDescription description) {
        this.id = id;
        this.name = name.asString();
        this.description = description.asString();
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
