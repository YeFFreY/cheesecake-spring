package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers.ActivityResourceModelAssembler;
import org.yeffrey.cheesecakespring.library.ActivityResourceStories;
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.AddResourceToActivityCommand;
import org.yeffrey.cheesecakespring.library.dto.AdjustActivityResourceQuantityCommand;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityResourcesController {
    private final ActivityResourceStories activityResourceStories;
    private final ActivityResourceModelAssembler activityResourceModelAssembler;

    ActivityResourcesController(ActivityResourceStories activityResourceStories, ActivityResourceModelAssembler activityResourceModelAssembler) {
        this.activityResourceStories = activityResourceStories;
        this.activityResourceModelAssembler = activityResourceModelAssembler;
    }

    @PostMapping("/{id}/resources")
    public ResponseEntity<Void> create(@PathVariable("id") Long id, @Valid @RequestBody AddResourceToActivityCommand command) {
        boolean resourceHasBeenAdded = this.activityResourceStories.activityRequiresResource(id, command);
        if (resourceHasBeenAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/resources")
    public ResponseEntity<List<EntityModel<ActivityResourceDetails>>> list(@PathVariable("id") Long id) {
        return ResponseEntity.ok(activityResourceModelAssembler.toList(this.activityResourceStories.findActivityResources(id)));
    }

    @DeleteMapping("/{id}/resources/{resourceId}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, @PathVariable("resourceId") Long resourceId) {
        if (activityResourceStories.resourceNotRequiredAnymore(id, resourceId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/resources/{resourceId}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @PathVariable("resourceId") Long resourceId,
                                       @Valid @RequestBody AdjustActivityResourceQuantityCommand command) {
        if (this.activityResourceStories.adjustActivityResourceQuantity(id, resourceId, command)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
