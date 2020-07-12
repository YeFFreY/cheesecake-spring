package org.yeffrey.cheesecakespring.infrastructure.web.rest;

import java.util.Objects;

public class EntityId {
    private Long id;

    public Long getId() {
        return id;
    }

    public static EntityId from(Long id) {
        EntityId entityId = new EntityId();
        entityId.id = id;
        return entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityId)) return false;
        EntityId entityId = (EntityId) o;
        return id.equals(entityId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
