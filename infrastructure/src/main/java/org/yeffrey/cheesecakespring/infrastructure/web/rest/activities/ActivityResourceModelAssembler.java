package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;

@Component
class ActivityResourceModelAssembler implements ListResourceModelAssembler<ActivityResourceDetails> {

    @Override
    public void addLinks(EntityModel<ActivityResourceDetails> resource) {
        // add links later
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ActivityResourceDetails>> resources) {
        // no links
    }

}
