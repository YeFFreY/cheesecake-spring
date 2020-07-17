package org.yeffrey.cheesecakespring.library.domain;


import org.yeffrey.cheesecakespring.common.domain.AuditedDomain;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class LibraryDomain extends AuditedDomain {
    /*(fetch = FetchType.LAZY)*/ //TODO LAZY is good practice if Library is bidi https://vladmihalcea.com/manytoone-jpa-hibernate/
    @ManyToOne
    @NotNull
    protected Library library;

    public Library getLibrary() {
        return library;
    }
}
