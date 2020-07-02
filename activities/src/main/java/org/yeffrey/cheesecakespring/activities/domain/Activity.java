package org.yeffrey.cheesecakespring.activities.domain;

import org.yeffrey.cheesecakespring.activities.core.OwnedDomain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "activities")
public class Activity extends OwnedDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_generator")
    @SequenceGenerator(name = "activities_generator", sequenceName = "activities_seq", allocationSize = 1)
    private Long id;

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
        super(UserId.system());
    }

    private Activity(ActivityName name, ActivityDescription description, UserId owner) {
        super(owner);
        this.name = name;
        this.description = description;
    }

    public static Activity from(ActivityName name, ActivityDescription description, UserId owner) {
        checkNotNull(name);
        checkNotNull(owner);
        return new Activity(name, description, owner);
    }

    public boolean addResource(Resource resource, int quantity) {
        checkNotNull(resource);
        if (!resource.belongsTo(this.ownerId)) {
            return false;
        }
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


    public void updateResource(Resource resource, int newQuantity) {
        this.resources.stream()
            .filter(ar -> ar.getResource().equals(resource))
            .findFirst()
            .ifPresent(ar -> ar.setQuantity(newQuantity));
    }
}
