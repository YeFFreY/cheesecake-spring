package org.yeffrey.cheesecakespring.infrastructure.web.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public interface ListResourceModelAssembler<T> extends SimpleRepresentationModelAssembler<T> {
    default List<EntityModel<T>> toList(Iterable<? extends T> entities) {
        Assert.notNull(entities, "entities must not be null!");
        List<EntityModel<T>> resourceList = new ArrayList<>();

        for (T entity : entities) {
            resourceList.add(toModel(entity));
        }

        return resourceList;
    }
}
