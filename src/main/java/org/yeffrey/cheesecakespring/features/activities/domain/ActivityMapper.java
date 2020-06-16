package org.yeffrey.cheesecakespring.features.activities.domain;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.CreateUpdateActivityCommand;

final class ActivityMapper {
    @Mapper
    public interface CreateUpdateActivityCommandMapper {
        Activity updateEntity(CreateUpdateActivityCommand command, @MappingTarget Activity activity);
    }

}
