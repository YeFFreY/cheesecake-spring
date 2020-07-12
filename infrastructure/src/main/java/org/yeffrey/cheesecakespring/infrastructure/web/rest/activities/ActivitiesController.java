package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers.ActivityDetailsModelAssembler;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers.ActivityOverviewModelAssembler;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.library.stories.ActivityStories;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {
    private final ActivityStories activityStories;
    private final ActivityDetailsModelAssembler activityDetailsModelAssembler;
    private final ActivityOverviewModelAssembler activityOverviewModelAssembler;

    ActivitiesController(
        ActivityStories activityStories,
        ActivityDetailsModelAssembler activityDetailsModelAssembler,
        ActivityOverviewModelAssembler activityOverviewModelAssembler) {
        this.activityStories = activityStories;
        this.activityDetailsModelAssembler = activityDetailsModelAssembler;
        this.activityOverviewModelAssembler = activityOverviewModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityId> create(@RequestBody CreateUpdateActivityCommand command) {
        Long id = this.activityStories.addToLibrary(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityId.from(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ActivityDetails>> show(@PathVariable("id") Long id) {
        //TODO Do I need to keep the ResponseEntity in the controller ?
        return ResponseEntity.ok(activityDetailsModelAssembler.toModel(this.activityStories.findDetails(id)));
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<ActivityOverview>>> list() {
        return ResponseEntity.ok(activityOverviewModelAssembler.toList(this.activityStories.findAllForCurrentUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @Valid @RequestBody CreateUpdateActivityCommand command) {
        this.activityStories.updateInformation(id, command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
