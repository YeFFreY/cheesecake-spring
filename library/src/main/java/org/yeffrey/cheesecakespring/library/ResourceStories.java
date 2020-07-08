package org.yeffrey.cheesecakespring.library;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.library.domain.Resource;
import org.yeffrey.cheesecakespring.library.domain.ResourceDescription;
import org.yeffrey.cheesecakespring.library.domain.ResourceName;
import org.yeffrey.cheesecakespring.library.domain.ResourceQuantityUnit;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.library.ports.ResourceRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ResourceStories {

    private final ResourceRepository resourceRepository;

    public ResourceStories(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Transactional
    public Long registerResource(CreateUpdateResourceCommand command) {
        Resource newResource = Resource.from(ResourceName.from(command.name), ResourceDescription.from(command.description), ResourceQuantityUnit.valueOf(command.quantityUnit));
        return this.resourceRepository.save(newResource).getId();
    }

    public Optional<ResourceDetails> findById(Long id) {
        return resourceRepository.findDetailsById(id);
    }

    @Transactional
    public void updateResource(Long id, CreateUpdateResourceCommand command) {
        resourceRepository.findById(id)
            .map(r -> r.updateDetails(ResourceName.from(command.name), ResourceDescription.from(command.description), ResourceQuantityUnit.valueOf(command.quantityUnit)))
            .ifPresent(resourceRepository::save);
    }

    public List<ResourceOverview> list() {
        return this.resourceRepository.findAll();
    }
}
