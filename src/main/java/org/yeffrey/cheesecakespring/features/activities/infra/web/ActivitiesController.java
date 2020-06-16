package org.yeffrey.cheesecakespring.features.activities.infra.web;

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
class ActivitiesController {
    private final ActivityStories activityStories;

    ActivitiesController(ActivityStories activityStories) {
        this.activityStories = activityStories;
    }

    @PostMapping
    ResponseEntity<EntityId> create(@RequestBody CreateUpdateActivityCommand command) {
        Long id = this.activityStories.registerActivity(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityId.from(id));
    }

    @GetMapping("/{id}")
    ResponseEntity<ActivityDetails> show(@PathVariable("id") Long id) {
        return this.activityStories.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    ResponseEntity<List<ActivityOverview>> list() {
        return ResponseEntity.ok(this.activityStories.list());
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody CreateUpdateActivityCommand command) {
        this.activityStories.updateActivity(id, command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
