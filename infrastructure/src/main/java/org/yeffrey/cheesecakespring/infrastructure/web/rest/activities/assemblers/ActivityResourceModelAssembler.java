package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities.assemblers;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.activities.dto.ActivityResourceDetails;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.ListResourceModelAssembler;

@Component
public class ActivityResourceModelAssembler implements ListResourceModelAssembler<ActivityResourceDetails> {

    @Override
    public void addLinks(@NonNull EntityModel<ActivityResourceDetails> resource) {
        // add links later
    }

    @Override
    public void addLinks(@NonNull CollectionModel<EntityModel<ActivityResourceDetails>> resources) {
        // no links
    }

}
