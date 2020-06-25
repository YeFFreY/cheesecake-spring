package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ResourceDetails;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class ResourceDetailsModelAssembler implements SimpleRepresentationModelAssembler<ResourceDetails> {

    @Override
    public void addLinks(EntityModel<ResourceDetails> resource) {
        resource.add(linkTo(methodOn(ResourcesController.class).show(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(ResourcesController.class).update(resource.getContent().getId(), null)).withRel("update"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ResourceDetails>> resources) {

    }

}
