package org.yeffrey.cheesecakespring.obsolete.features.activities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ActivityModelAssembler implements SimpleRepresentationModelAssembler<Activity> {

    @Override
    public void addLinks(EntityModel<Activity> resource) {
/*
        resource.add(linkTo(methodOn(ActivitiesController.class).activity(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(ActivitiesController.class).update(null, resource.getContent().getId())).withRel("update"));
*/
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Activity>> resources) {
    }
}
