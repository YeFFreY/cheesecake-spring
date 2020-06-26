package org.yeffrey.cheesecakespring.activities.core;

import org.yeffrey.cheesecakespring.activities.domain.UserId;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import static com.google.common.base.Preconditions.checkNotNull;

@MappedSuperclass
public abstract class OwnedDomain extends BaseDomain {
    @Column(name = "owner_id")
    protected UserId ownerId;

    public boolean belongsTo(UserId ownerId) {
        return ownerId != null && ownerId.equals(this.ownerId);
    }

    public UserId getOwnerId() {
        return ownerId;
    }

    public OwnedDomain(UserId ownerId) {
        checkNotNull(ownerId);
        this.ownerId = ownerId;
    }
}
