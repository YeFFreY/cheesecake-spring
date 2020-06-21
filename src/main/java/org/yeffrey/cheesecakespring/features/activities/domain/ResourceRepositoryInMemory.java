package org.yeffrey.cheesecakespring.features.activities.domain;

import org.yeffrey.cheesecakespring.features.activities.domain.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

class ResourceRepositoryInMemory implements ResourceRepository {
    private final ConcurrentHashMap<Long, Resource> db = new ConcurrentHashMap<>();
    private final AtomicLong resourceSequence = new AtomicLong();

    @Override
    public Resource save(Resource entity) {
        if (entity.getId() == null) {
            entity.setId(resourceSequence.getAndIncrement());
        }
        db.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<ResourceDetails> findDetailsByIdAndOwnerId(Long id, String ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(r -> r.belongsTo(ownerId))
            .map(r -> new ResourceDetails(r.getId(), r.getName(), r.getDescription(), r.getQuantityUnit().name()));
    }

    @Override
    public Optional<Resource> findByIdAndOwnerId(long id, String ownerId) {
        return Optional.ofNullable(this.db.get(id))
            .filter(r -> r.belongsTo(ownerId));
    }

    @Override
    public List<ResourceOverview> findAllByOwnerId(String ownerId) {
        return this.db.values().stream()
            .filter(r -> r.belongsTo(ownerId))
            .map(r -> new ResourceOverview(r.getId(), r.getName()))
            .collect(Collectors.toList());
    }


}