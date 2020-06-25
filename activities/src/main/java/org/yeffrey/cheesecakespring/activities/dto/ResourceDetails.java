package org.yeffrey.cheesecakespring.activities.dto;

import org.yeffrey.cheesecakespring.activities.ResourceQuantityUnit;
import org.yeffrey.cheesecakespring.activities.domain.ResourceDescription;
import org.yeffrey.cheesecakespring.activities.domain.ResourceName;

public class ResourceDetails implements ModelDto {
    private final Long id;
    private final String name;
    private final String description;
    private final ResourceQuantityUnit quantityUnit;

    public ResourceDetails(Long id, ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit) {
        this.id = id;
        this.name = name.asString();
        this.description = description.asString();
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

    public ResourceQuantityUnit getQuantityUnit() {
        return quantityUnit;
    }
}
