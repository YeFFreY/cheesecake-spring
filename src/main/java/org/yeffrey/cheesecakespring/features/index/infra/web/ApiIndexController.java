package org.yeffrey.cheesecakespring.features.index.infra.web;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeffrey.cheesecakespring.features.activities.infra.web.ActivitiesController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
class ApiIndexController {

    @GetMapping
    public ResponseEntity<RepresentationModel<?>> index() {

        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(methodOn(ActivitiesController.class).list()).withRel("activities"));

        return ResponseEntity.ok(model);
    }
}
