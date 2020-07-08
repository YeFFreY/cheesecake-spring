package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.ActivitiesController;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActivityOverviewModelAssembler implements ListResourceModelAssembler<ActivityOverview> {

    @Override
    public void addLinks(EntityModel<ActivityOverview> resource) {
        if (Objects.nonNull(resource.getContent())) {
            resource.add(WebMvcLinkBuilder.linkTo(methodOn(ActivitiesController.class).show(resource.getContent().getId())).withSelfRel());
        }
    }

    @Override
    public void addLinks(@NonNull CollectionModel<EntityModel<ActivityOverview>> resources) {
        // no links
    }

}
