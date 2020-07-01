package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.ResourcesController;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ResourceDetailsModelAssembler implements ListResourceModelAssembler<ResourceDetails> {

    @Override
    public void addLinks(@NonNull EntityModel<ResourceDetails> resource) {
        if (Objects.nonNull(resource.getContent())) {
            resource.add(WebMvcLinkBuilder.linkTo(methodOn(ResourcesController.class).show(resource.getContent().getId())).withSelfRel());
            resource.add(linkTo(methodOn(ResourcesController.class).update(resource.getContent().getId(), null)).withRel("update"));
        }
    }

    @Override
    public void addLinks(@NonNull CollectionModel<EntityModel<ResourceDetails>> resources) {
        // no links
    }

}
