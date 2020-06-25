package org.yeffrey.cheesecakespring.activities;

import org.yeffrey.cheesecakespring.activities.domain.ActivityDescription;
import org.yeffrey.cheesecakespring.activities.domain.ActivityName;
import org.yeffrey.cheesecakespring.activities.domain.UserId;

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


    @OneToMany(
        mappedBy = "activity",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<ActivityResource> resources = new HashSet<>();

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
        ActivityResource activityResource = ActivityResource.from(this, resource, quantity);
        return this.resources.add(activityResource);
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

    void setId(Long id) {
        this.id = id;
    }

    public ActivityName getName() {
        return name;
    }

    public ActivityDescription getDescription() {
        return description;
    }

    public Set<ActivityResource> getResources() {
        return this.resources;
    }
}
