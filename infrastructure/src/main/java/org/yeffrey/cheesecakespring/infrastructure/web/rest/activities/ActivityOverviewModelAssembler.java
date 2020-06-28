package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ActivityOverviewModelAssembler implements ListResourceModelAssembler<ActivityOverview> {

    @Override
    public void addLinks(EntityModel<ActivityOverview> resource) {
        if (Objects.nonNull(resource.getContent())) {
            resource.add(linkTo(methodOn(ActivitiesController.class).show(resource.getContent().getId())).withSelfRel());
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ActivityOverview>> resources) {
        // no links
    }

}
