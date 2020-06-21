package org.yeffrey.cheesecakespring.features.activities.domain;

import com.google.common.base.Preconditions;
import org.yeffrey.cheesecakespring.features.common.OwnedDomain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activities")
class Activity extends OwnedDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_generator")
    @SequenceGenerator(name = "activities_generator", sequenceName = "activities_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;

    @OneToMany(
        mappedBy = "activity",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<ActivityResource> resources = new HashSet<>();

    protected Activity() {
    }

    public Activity(String ownerId) {
        this.ownerId = ownerId;
    }

    public static Activity from(String name, String description, String ownerId) {
        Preconditions.checkNotNull(ownerId);
        Activity activity = new Activity(ownerId);
        activity.setDetails(name, description);
        return activity;
    }

    public Activity updateDetails(String name, String description) {
        this.setDetails(name, description);
        return this;
    }

    public boolean addResource(Resource resource, int quantity) {
        ActivityResource activityResource = ActivityResource.from(this, resource, quantity);
        return this.resources.add(activityResource);
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

    public Set<ActivityResource> getResources() {
        return this.resources;
    }

    protected void setDetails(String name, String description) {
        Preconditions.checkNotNull(name);
        this.name = name;
        this.description = description;
    }
}
