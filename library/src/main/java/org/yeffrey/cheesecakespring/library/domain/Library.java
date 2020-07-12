package org.yeffrey.cheesecakespring.library.domain;

import org.yeffrey.cheesecakespring.library.core.AuditedDomain;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity
@Table(name = "libraries")
public class Library extends AuditedDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libraries_generator")
    @SequenceGenerator(name = "libraries_generator", sequenceName = "libraries_seq", allocationSize = 1)
    private Long id;

    @Column(name = "owner_id")
    private UserId ownerId;

    protected Library() {
    }

    private Library(UserId ownerId) {
        this.ownerId = ownerId;
    }

    public static Library from(UserId ownerId) {
        checkNotNull(ownerId);
        return new Library(ownerId);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public UserId getOwnerId() {
        return ownerId;
    }

}
