package org.yeffrey.cheesecakespring.activities;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.activities.domain.Resource;
import org.yeffrey.cheesecakespring.activities.domain.ResourceDescription;
import org.yeffrey.cheesecakespring.activities.domain.ResourceName;
import org.yeffrey.cheesecakespring.activities.domain.ResourceQuantityUnit;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;
import org.yeffrey.cheesecakespring.activities.ports.ResourceRepository;

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
