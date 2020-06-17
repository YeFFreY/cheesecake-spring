package org.yeffrey.cheesecakespring.features.activities.domain;

import org.yeffrey.cheesecakespring.features.common.BaseDomain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "activities")
class Activity extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_generator")
    @SequenceGenerator(name = "activities_generator", sequenceName = "activities_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;

    @NotBlank
    @Size(max = 255)
    @Column(name = "owner_id")
    private String ownerId; // FIXME should this become an interface "OwnedDomain" for instance ?

    protected Activity() {
    }

    public static Activity from(String name, String description, String ownerId) {
        Activity activity = new Activity();
        activity.name = name;
        activity.description = description;
        activity.ownerId = ownerId;
        return activity;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    boolean belongsTo(String ownerId) {
        return ownerId != null && ownerId.equals(this.ownerId);
    }

    String getOwnerId() {
        return ownerId;
    }
}
