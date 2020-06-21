package org.yeffrey.cheesecakespring.features.activities.domain.dto;

public class ActivityDetails implements ModelDto {
    private final Long id;
    private final String name;
    private final String description;

    public ActivityDetails(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
