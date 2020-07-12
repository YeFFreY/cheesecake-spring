package org.yeffrey.cheesecakespring.library.stories;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.library.core.ResourceNotFoundException;
import org.yeffrey.cheesecakespring.library.domain.*;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.library.ports.ResourceRepositoryPort;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ResourceStories {
    private final LibraryStories libraryStories;
    private final ResourceRepositoryPort resourceRepositoryPort;

    public ResourceStories(LibraryStories libraryStories,
                           ResourceRepositoryPort activityRepositoryPort) {
        this.libraryStories = libraryStories;
        this.resourceRepositoryPort = activityRepositoryPort;
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Long addToLibrary(CreateUpdateResourceCommand command) {
        Library library = this.libraryStories.findForCurrentUser();
        Resource resource = Resource.from(library, ResourceName.from(command.name), ResourceDescription.from(command.description), ResourceQuantityUnit.valueOf(command.quantityUnit));
        return this.resourceRepositoryPort.save(resource).getId();
    }

    @PreAuthorize("@policy.canManageResource(authentication, #resourceId)")
    public ResourceDetails findDetails(Long resourceId) {
        return this.resourceRepositoryPort.findDetailsById(resourceId).orElseThrow(ResourceNotFoundException::new);
    }

    @PreAuthorize("isAuthenticated()")
    public List<ResourceOverview> findAllForCurrentUser() {
        Library library = this.libraryStories.findForCurrentUser();
        return this.resourceRepositoryPort.findResourcesByLibrary(library);
    }

    @PreAuthorize("@policy.canManageResource(authentication, #resourceId)")
    @Transactional
    public void updateInformation(Long resourceId,
                                  CreateUpdateResourceCommand command) {
        Resource resource = this.resourceRepositoryPort.findById(resourceId).orElseThrow(ResourceNotFoundException::new);
        resource.updateDetails(ResourceName.from(command.name), ResourceDescription.from(command.description), ResourceQuantityUnit.valueOf(command.quantityUnit));
        this.resourceRepositoryPort.save(resource);
    }
}
