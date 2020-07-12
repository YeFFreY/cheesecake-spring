package org.yeffrey.cheesecakespring.library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "activities")
public class Activity extends LibraryDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_generator")
    @SequenceGenerator(name = "activities_generator", sequenceName = "activities_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private ActivityName name;

    private ActivityDescription description;

    /* FIXME Should this list become an object by itself so it can have internal method to check duplicates, and other validatiosn that would live inside it instead of the activity ?*/
    @OneToMany(
        mappedBy = "activity",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<ActivityResource> resources = new HashSet<>(5);

    protected Activity() {
    }

    private Activity(Library library,
                     ActivityName name,
                     ActivityDescription description) {
        this.library = library;
        this.name = name;
        this.description = description;
    }

    public static Activity from(Library library,
                                ActivityName name,
                                ActivityDescription description) {
        checkNotNull(library);
        checkNotNull(name);
        return new Activity(library, name, description);
    }

    public boolean addResource(Resource resource,
                               int quantity) {
        checkNotNull(resource);
        ActivityResource activityResource = ActivityResource.from(this, resource, quantity);
        return this.resources.add(activityResource);
    }

    public boolean removeResource(Resource resource) {
        checkNotNull(resource);
        return this.resources.removeIf(activityResource -> activityResource.getResource().equals(resource));
    }

    public Activity updateDetails(ActivityName name, ActivityDescription description) {
        checkNotNull(name);
        this.name = name;
        this.description = description;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public ActivityName getName() {
        return name;
    }

    public ActivityDescription getDescription() {
        return description;
    }

    public Set<ActivityResource> getResources() {
        return Set.copyOf(this.resources);
    }

    public boolean updateResource(Resource resource, int newQuantity) {
        return this.resources.stream()
            .filter(ar -> ar.getResource().equals(resource))
            .findFirst()
            .map(ar -> {
                ar.setQuantity(newQuantity);
                return true;
            }).orElse(false);
    }
}
