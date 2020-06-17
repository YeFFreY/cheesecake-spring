package org.yeffrey.cheesecakespring.features.activities.infra.web;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeffrey.cheesecakespring.features.activities.domain.ActivityStories;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.features.common.EntityId;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {
    private final ActivityStories activityStories;
    private final ActivityDetailsModelAssembler activityDetailsModelAssembler;
    private final ActivityOverviewModelAssembler activityOverviewModelAssembler;

    ActivitiesController(ActivityStories activityStories, ActivityDetailsModelAssembler activityDetailsModelAssembler, ActivityOverviewModelAssembler activityOverviewModelAssembler) {
        this.activityStories = activityStories;
        this.activityDetailsModelAssembler = activityDetailsModelAssembler;
        this.activityOverviewModelAssembler = activityOverviewModelAssembler;
    }

    @PostMapping
    public ResponseEntity<EntityId> create(@RequestBody CreateUpdateActivityCommand command) {
        Long id = this.activityStories.registerActivity(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityId.from(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ActivityDetails>> show(@PathVariable("id") Long id) {
        return this.activityStories.findById(id)
            .map(activityDetailsModelAssembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<EntityModel<ActivityOverview>>> list() {
        return ResponseEntity.ok(activityOverviewModelAssembler.toList(this.activityStories.list()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody CreateUpdateActivityCommand command) {
        this.activityStories.updateActivity(id, command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
