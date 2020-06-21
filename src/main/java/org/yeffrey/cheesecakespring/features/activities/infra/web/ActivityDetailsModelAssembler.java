package org.yeffrey.cheesecakespring.features.activities.infra.web;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ActivityDetailsModelAssembler implements SimpleRepresentationModelAssembler<ActivityDetails> {

    @Override
    public void addLinks(EntityModel<ActivityDetails> resource) {
        resource.add(linkTo(methodOn(ActivitiesController.class).show(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(ActivitiesController.class).update(resource.getContent().getId(), null)).withRel("update"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ActivityDetails>> resources) {

    }

}
