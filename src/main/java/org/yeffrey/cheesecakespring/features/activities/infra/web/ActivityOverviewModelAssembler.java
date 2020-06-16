package org.yeffrey.cheesecakespring.features.activities.infra.web;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityOverview;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActivityOverviewModelAssembler implements SimpleRepresentationModelAssembler<ActivityOverview> {

    @Override
    public void addLinks(EntityModel<ActivityOverview> resource) {
        resource.add(linkTo(methodOn(ActivitiesController.class).show(resource.getContent().getId())).withSelfRel());
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ActivityOverview>> resources) {

    }

    public List<EntityModel<ActivityOverview>> toList(Iterable<? extends ActivityOverview> entities) {
        Assert.notNull(entities, "entities must not be null!");
        List<EntityModel<ActivityOverview>> resourceList = new ArrayList<>();

        for (ActivityOverview entity : entities) {
            resourceList.add(toModel(entity));
        }

        return resourceList;
    }
}
