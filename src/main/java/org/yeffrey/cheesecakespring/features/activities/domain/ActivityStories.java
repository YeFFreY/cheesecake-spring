package org.yeffrey.cheesecakespring.features.activities.domain;

import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.features.common.AuthenticatedUserPort;
import org.yeffrey.cheesecakespring.obsolete.domain.exception.AccessDeniedException;

import java.util.List;
import java.util.Optional;


//@Service // On essaie de le constuire uniquement dans la config, est-ce que Transactional fonctionnera ?
@Transactional(readOnly = true)
public class ActivityStories {

    private final ActivityRepository activityRepository;
    private final AuthenticatedUserPort authenticatedUserPort;

    public ActivityStories(ActivityRepository activityRepository, AuthenticatedUserPort authenticatedUserPort) {
        this.activityRepository = activityRepository;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Transactional
    public Long registerActivity(CreateUpdateActivityCommand command){
        String userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        Activity newActivity = Activity.from(command.name, command.description, userId);
        return this.activityRepository.save(newActivity).getId();
    }

    public Optional<ActivityDetails> findById(Long id) {
        return this.authenticatedUserPort.getAuthenticatedUserId()
            .flatMap(userId -> activityRepository.findDetailsByIdAndOwnerId(id, userId));
    }

    public void updateActivity(Long id, CreateUpdateActivityCommand command) {
        ActivityMapper.CreateUpdateActivityCommandMapper mapper = Mappers.getMapper( ActivityMapper.CreateUpdateActivityCommandMapper.class );

        String userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        activityRepository.findByIdAndOwnerId(id, userId)
            .map(a -> mapper.updateEntity(command, a))
            .ifPresent(activityRepository::save);
    }

    public List<ActivityOverview> list() {
        String userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        return this.activityRepository.findAllByOwnerId(userId);
    }
}
