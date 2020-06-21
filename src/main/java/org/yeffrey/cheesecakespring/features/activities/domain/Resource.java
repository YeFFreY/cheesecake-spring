package org.yeffrey.cheesecakespring.features.activities.domain;

import com.google.common.base.Preconditions;
import org.yeffrey.cheesecakespring.features.common.OwnedDomain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "resources")
class Resource extends OwnedDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resources_generator")
    @SequenceGenerator(name = "resources_generator", sequenceName = "resources_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;

    @Column(name = "quantity_unit")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ResourceQuantityUnit quantityUnit = ResourceQuantityUnit.Item;

    protected Resource() {
    }

    public Resource(String ownerId) {
        this.ownerId = ownerId;
    }

    public static Resource from(String name, String description, ResourceQuantityUnit quantityUnit, String ownerId) {
        Preconditions.checkNotNull(ownerId);
        Resource resource = new Resource(ownerId);
        resource.setDetails(name, description, quantityUnit);
        return resource;
    }

    public Resource updateDetails(String name, String description, ResourceQuantityUnit quantityUnit) {
        this.setDetails(name, description, quantityUnit);
        return this;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
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

    protected void setDetails(String name, String description, ResourceQuantityUnit quantityUnit) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(quantityUnit);
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
    }
}
