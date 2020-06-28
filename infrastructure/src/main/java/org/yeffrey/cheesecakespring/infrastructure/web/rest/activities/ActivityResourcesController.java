package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeffrey.cheesecakespring.activities.ActivityResourceStories;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.activities.dto.AddResourceToActivityCommand;

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
}
