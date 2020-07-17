package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.Resource;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.library.ports.ResourceRepositoryPort;

import java.util.List;
import java.util.Optional;

@PortAdapter
@Component
public class ResourceRepositoryAdapter implements ResourceRepositoryPort {
    private final ResourceRepositoryJpa resourceRepository;

    public ResourceRepositoryAdapter(ResourceRepositoryJpa resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public boolean resourceBelongsToUserLibrary(Long resourceId,
                                                UserId userId) {
        return this.resourceRepository.resourceBelongsToUserLibrary(resourceId, userId);
    }

    @Override
    public Resource save(Resource entity) {
        return this.resourceRepository.save(entity);
    }

    @Override
    public Optional<ResourceDetails> findDetailsById(Long id) {
        return this.resourceRepository.findDetailsById(id);
    }

    @Override
    public List<ResourceOverview> findResourcesByLibrary(Library library) {
        return this.resourceRepository.findOverviewByLibrary(library);
    }

    @Override
    public Optional<Resource> findById(Long id) {
        return this.resourceRepository.findById(id);
    }
}
