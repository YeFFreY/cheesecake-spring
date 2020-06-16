package org.yeffrey.cheesecakespring.features.common;

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

}
