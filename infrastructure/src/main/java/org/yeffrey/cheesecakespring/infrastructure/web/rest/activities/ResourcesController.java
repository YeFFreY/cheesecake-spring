package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers.ResourceDetailsModelAssembler;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers.ResourceOverviewModelAssembler;
import org.yeffrey.cheesecakespring.library.ResourceStories;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateResourceCommand;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourcesController {
    private final ResourceStories resourceStories;
    private final ResourceDetailsModelAssembler resourceDetailsModelAssembler;
    private final ResourceOverviewModelAssembler resourceOverviewModelAssembler;

    ResourcesController(ResourceStories resourceStories, ResourceDetailsModelAssembler resourceDetailsModelAssembler, ResourceOverviewModelAssembler resourceOverviewModelAssembler) {
        this.resourceStories = resourceStories;
        this.resourceDetailsModelAssembler = resourceDetailsModelAssembler;
        this.resourceOverviewModelAssembler = resourceOverviewModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityId> create(@RequestBody CreateUpdateResourceCommand command) {
        Long id = this.resourceStories.registerResource(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityId.from(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ResourceDetails>> show(@PathVariable("id") Long id) {
        return this.resourceStories.findById(id)
            .map(resourceDetailsModelAssembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<ResourceOverview>>> list() {
        return ResponseEntity.ok(resourceOverviewModelAssembler.toList(this.resourceStories.list()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody CreateUpdateResourceCommand command) {
        this.resourceStories.updateResource(id, command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
