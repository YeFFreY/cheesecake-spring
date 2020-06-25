package org.yeffrey.cheesecakespring.activities;

import org.yeffrey.cheesecakespring.activities.domain.ResourceDescription;
import org.yeffrey.cheesecakespring.activities.domain.ResourceName;
import org.yeffrey.cheesecakespring.activities.domain.UserId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "resources")
public class Resource extends OwnedDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resources_generator")
    @SequenceGenerator(name = "resources_generator", sequenceName = "resources_seq", allocationSize = 1)
    private Long id;

    private ResourceName name;

    private ResourceDescription description;

    @Column(name = "quantity_unit")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ResourceQuantityUnit quantityUnit = ResourceQuantityUnit.Item;

    protected Resource() {
        super(UserId.system());
    }

    private Resource(ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit, UserId owner) {
        super(owner);
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
    }

    public static Resource from(ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit, UserId owner) {
        checkNotNull(name);
        checkNotNull(quantityUnit);
        return new Resource(name, description, quantityUnit, owner);
    }


    public Resource updateDetails(ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit) {
        checkNotNull(name);
        checkNotNull(quantityUnit);
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
        return this;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public ResourceName getName() {
        return name;
    }

    public ResourceDescription getDescription() {
        return description;
    }

    public ResourceQuantityUnit getQuantityUnit() {
        return quantityUnit;
    }

}
