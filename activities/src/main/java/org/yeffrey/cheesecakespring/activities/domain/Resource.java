package org.yeffrey.cheesecakespring.activities.domain;

import org.yeffrey.cheesecakespring.activities.core.AuditedDomain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "resources")
public class Resource extends AuditedDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resources_generator")
    @SequenceGenerator(name = "resources_generator", sequenceName = "resources_seq", allocationSize = 1)
    private Long id;

    private ResourceName name;

    private ResourceDescription description;

    @Column(name = "quantity_unit")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ResourceQuantityUnit quantityUnit;

    protected Resource() {
    }

    private Resource(ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit) {
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
    }

    public static Resource from(ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit) {
        checkNotNull(name);
        checkNotNull(quantityUnit);
        return new Resource(name, description, quantityUnit);
    }


    public Resource updateDetails(ResourceName name, ResourceDescription description, ResourceQuantityUnit quantityUnit) {
        checkNotNull(name);
        checkNotNull(quantityUnit);
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
        return this;
    }

    @Override
    public Long getId() {
        return id;
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
