package org.yeffrey.cheesecakespring.activities;

import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.activities.core.AccessDeniedException;
import org.yeffrey.cheesecakespring.activities.domain.*;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.activities.ports.AuthenticatedUserService;
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository;

import java.util.List;
import java.util.Optional;

//@Service // On essaie de le constuire uniquement dans la config, est-ce que Transactional fonctionnera ?
@Transactional(readOnly = true)
public class ResourceStories {

    private final ResourceRepository resourceRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ResourceStories(ResourceRepository resourceRepository, AuthenticatedUserService authenticatedUserService) {
        this.resourceRepository = resourceRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional
    public Long registerResource(CreateUpdateResourceCommand command) {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        Resource newResource = Resource.from(ResourceName.from(command.name), ResourceDescription.from(command.description), ResourceQuantityUnit.valueOf(command.quantityUnit), userId);
        return this.resourceRepository.save(newResource).getId();
    }

    public Optional<ResourceDetails> findById(Long id) {
        return this.authenticatedUserService.getAuthenticatedUserId()
            .flatMap(userId -> resourceRepository.findDetailsByIdAndOwnerId(id, userId));
    }

    @Transactional
    public void updateResource(Long id, CreateUpdateResourceCommand command) {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        resourceRepository.findByIdAndOwnerId(id, userId)
            .map(r -> r.updateDetails(ResourceName.from(command.name), ResourceDescription.from(command.description), ResourceQuantityUnit.valueOf(command.quantityUnit)))
            .ifPresent(resourceRepository::save);
    }

    public List<ResourceOverview> list() {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        return this.resourceRepository.findAllByOwnerId(userId);
    }
}
