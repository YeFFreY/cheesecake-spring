package org.yeffrey.cheesecakespring.features.activities.domain.dto;

public class ResourceDetails implements ModelDto {
    private final Long id;
    private final String name;
    private final String description;
    private final String quantityUnit;

    public ResourceDetails(Long id, String name, String description, String quantityUnit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
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

    public String getQuantityUnit() {
        return quantityUnit;
    }
}
