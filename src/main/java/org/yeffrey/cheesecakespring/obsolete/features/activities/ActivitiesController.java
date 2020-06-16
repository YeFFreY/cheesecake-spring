package org.yeffrey.cheesecakespring.obsolete.features.activities;

import com.google.common.collect.Lists;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.obsolete.domain.stories.RegisterActivityStory;
import org.yeffrey.cheesecakespring.features.common.EntityId;

/*@RestController
@RequestMapping("/activities")*/
public class ActivitiesController {
    /*private final RegisterActivityStory registerActivityStory;
    private final ActivityModelAssembler activityModelAssembler;

    public ActivitiesController(RegisterActivityStory registerActivityStory, ActivityModelAssembler activityModelAssembler) {
        this.registerActivityStory = registerActivityStory;
        this.activityModelAssembler = activityModelAssembler;
    }

    @GetMapping("{id}")
    ResponseEntity<EntityModel<Activity>> activity(@PathVariable("id") Long id) {
        ActivityDetails activity = new ActivityDetails(id, "The activity requested", "This is the description");
        return ResponseEntity.ok(activityModelAssembler.toModel(activity));
    }

    @PostMapping
    ResponseEntity<EntityId> create(@RequestBody RegisterActivityStory.RegisterActivityCommand command) {
        Long id = this.registerActivityStory.registerActivity(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityId.from(id));
    }


    @PutMapping("{id}")
    ResponseEntity<EntityId> update(@RequestBody UpdateActivityCommand command, @PathVariable("id") Long id) {
        return ResponseEntity.accepted().body(EntityId.from(id));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Activity>>> activities() {
        return ResponseEntity.ok(activityModelAssembler.toCollectionModel(Lists.newArrayList(
            new ActivitySummary(1L, "Activity One"),
            new ActivitySummary(2L, "Activity Two")
        )));
    }*/
}
