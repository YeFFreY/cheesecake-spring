package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.ResourcesController;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResourceOverviewModelAssembler implements ListResourceModelAssembler<ResourceOverview> {

    @Override
    public void addLinks(EntityModel<ResourceOverview> resource) {
        if (Objects.nonNull(resource.getContent())) {
            resource.add(WebMvcLinkBuilder.linkTo(methodOn(ResourcesController.class).show(resource.getContent().getId())).withSelfRel());
        }
    }

    @Override
    public void addLinks(@NonNull CollectionModel<EntityModel<ResourceOverview>> resources) {
        // no links
    }

}
