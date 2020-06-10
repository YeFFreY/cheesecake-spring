package org.yeffrey.cheesecakespring.features;

import com.google.common.collect.Lists;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeffrey.cheesecakespring.features.activities.ActivitiesController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
class ApiIndexController {

    @GetMapping
    public RepresentationModel index() {

        return RepresentationModel.of(null, Lists.newArrayList(
            linkTo(methodOn(ActivitiesController.class).activities()).withRel("activities")
/*
            Map.of("rel", "activities::details", "href", "http://localhost:8080/api/activities"),
            Map.of("rel", "activities::create", "href", "http://localhost:8080/api/activities")
*/
        ));
    }
}
