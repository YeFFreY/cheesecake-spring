package org.yeffrey.cheesecakespring.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "resources")
public class Resource extends LibraryDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resources_generator")
    @SequenceGenerator(name = "resources_generator", sequenceName = "resources_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private ResourceName name;

    private ResourceDescription description;

    @Column(name = "quantity_unit")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ResourceQuantityUnit quantityUnit;

    protected Resource() {
    }

    private Resource(Library library,
                     ResourceName name,
                     ResourceDescription description,
                     ResourceQuantityUnit quantityUnit) {
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
        this.library = library;
    }

    public static Resource from(Library library,
                                ResourceName name,
                                ResourceDescription description,
                                ResourceQuantityUnit quantityUnit) {
        checkNotNull(library);
        checkNotNull(name);
        checkNotNull(quantityUnit);
        return new Resource(library, name, description, quantityUnit);
    }


    public Resource updateDetails(ResourceName name,
                                  ResourceDescription description,
                                  ResourceQuantityUnit quantityUnit) {
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
