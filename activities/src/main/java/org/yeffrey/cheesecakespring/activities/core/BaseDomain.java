package org.yeffrey.cheesecakespring.activities.core;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseDomain implements Serializable {

    private String uuid = UUID.randomUUID().toString();
    @Version
    private Integer version;

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object that) {
        return this == that
            || that instanceof BaseDomain && Objects.equals(uuid, ((BaseDomain) that).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public abstract Long getId();
}
