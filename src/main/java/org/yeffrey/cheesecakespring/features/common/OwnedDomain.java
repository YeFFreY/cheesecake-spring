package org.yeffrey.cheesecakespring.features.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class OwnedDomain extends BaseDomain {
    @NotBlank
    @Size(max = 255)
    @Column(name = "owner_id")
    protected String ownerId;

    public boolean belongsTo(String ownerId) {
        return ownerId != null && ownerId.equals(this.ownerId);
    }

    public String getOwnerId() {
        return ownerId;
    }

}
