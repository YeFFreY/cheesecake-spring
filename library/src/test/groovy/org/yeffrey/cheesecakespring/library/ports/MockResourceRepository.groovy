package org.yeffrey.cheesecakespring.library.ports

import org.yeffrey.cheesecakespring.common.domain.UserId
import org.yeffrey.cheesecakespring.library.domain.Library
import org.yeffrey.cheesecakespring.library.domain.Resource
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview

import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Collectors

class MockResourceRepository implements ResourceRepositoryPort {
    AtomicLong seq = new AtomicLong()
    Map<Long, Resource> db = [:]

    @Override
    Resource save(Resource resource) {
        if (resource.id == null) {
            resource.id = seq.getAndIncrement()
        }
        db[resource.id] = resource
        return resource
    }

    @Override
    boolean resourceBelongsToUserLibrary(Long id, UserId userId) {
        return false
    }

    @Override
    Optional<ResourceDetails> findDetailsById(Long id) {
        return this.findById(id)
                .map(r -> new ResourceDetails(r.id, r.name, r.description, r.quantityUnit))
    }

    @Override
    List<ResourceOverview> findResourcesByLibrary(Library library) {
        return db.findAll { (it.value.library == library) }.values().stream().map(r -> new ResourceOverview(r.id, r.name)).collect(Collectors.toList())
    }

    @Override
    Optional<Resource> findById(Long id) {
        return Optional.ofNullable(db[id])
    }
}
