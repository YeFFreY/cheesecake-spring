package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ActivityDetailsModelAssembler implements ListResourceModelAssembler<ActivityDetails> {

    @Override
    public void addLinks(EntityModel<ActivityDetails> resource) {
        if (Objects.nonNull(resource.getContent())) {
            resource.add(linkTo(methodOn(ActivitiesController.class).show(resource.getContent().getId())).withSelfRel());
            resource.add(linkTo(methodOn(ActivitiesController.class).update(resource.getContent().getId(), null)).withRel("update"));
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ActivityDetails>> resources) {
        // no links
    }

}
