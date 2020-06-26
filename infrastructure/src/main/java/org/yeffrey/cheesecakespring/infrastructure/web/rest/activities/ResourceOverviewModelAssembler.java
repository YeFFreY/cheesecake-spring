package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ResourceOverview;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ResourceOverviewModelAssembler implements SimpleRepresentationModelAssembler<ResourceOverview> {

    @Override
    public void addLinks(EntityModel<ResourceOverview> resource) {
        if (Objects.nonNull(resource.getContent())) {
            resource.add(linkTo(methodOn(ResourcesController.class).show(resource.getContent().getId())).withSelfRel());
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ResourceOverview>> resources) {
        // no links
    }

}
