package org.yeffrey.cheesecakespring.features.activities.domain;

import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.features.common.AuthenticatedUserPort;
import org.yeffrey.cheesecakespring.obsolete.domain.exception.AccessDeniedException;

import java.util.List;
import java.util.Optional;


//@Service // On essaie de le constuire uniquement dans la config, est-ce que Transactional fonctionnera ?
@Transactional(readOnly = true)
public class ResourceStories {

    private final ResourceRepository resourceRepository;
    private final AuthenticatedUserPort authenticatedUserPort;

    public ResourceStories(ResourceRepository resourceRepository, AuthenticatedUserPort authenticatedUserPort) {
        this.resourceRepository = resourceRepository;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Transactional
    public Long registerResource(CreateUpdateResourceCommand command) {
        String userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        Resource newResource = Resource.from(command.name, command.description, ResourceQuantityUnit.valueOf(command.quantityUnit), userId);
        return this.resourceRepository.save(newResource).getId();
    }

    public Optional<ResourceDetails> findById(Long id) {
        return this.authenticatedUserPort.getAuthenticatedUserId()
            .flatMap(userId -> resourceRepository.findDetailsByIdAndOwnerId(id, userId));
    }

    public void updateResource(Long id, CreateUpdateResourceCommand command) {
        String userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        resourceRepository.findByIdAndOwnerId(id, userId)
            .map(r -> r.updateDetails(command.name, command.description, ResourceQuantityUnit.valueOf(command.quantityUnit)))
            .ifPresent(resourceRepository::save);
    }

    public List<ResourceOverview> list() {
        String userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        return this.resourceRepository.findAllByOwnerId(userId);
    }
}
