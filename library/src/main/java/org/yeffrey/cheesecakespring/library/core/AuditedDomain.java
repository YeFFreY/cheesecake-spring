package org.yeffrey.cheesecakespring.library.core;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.yeffrey.cheesecakespring.library.domain.UserId;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedDomain extends BaseDomain {
    @Column(name = "created_by")
    @CreatedBy
    protected UserId createdBy;
}
