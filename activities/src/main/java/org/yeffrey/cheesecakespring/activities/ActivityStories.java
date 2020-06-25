package org.yeffrey.cheesecakespring.activities;

import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.activities.domain.ActivityDescription;
import org.yeffrey.cheesecakespring.activities.domain.ActivityName;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand;

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
    public Long registerActivity(CreateUpdateActivityCommand command) {
        UserId userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        Activity newActivity = Activity.from(ActivityName.from(command.name), ActivityDescription.from(command.description), userId);
        return this.activityRepository.save(newActivity).getId();
    }

    public Optional<ActivityDetails> findById(Long id) {
        return this.authenticatedUserPort.getAuthenticatedUserId()
            .flatMap(userId -> activityRepository.findDetailsByIdAndOwnerId(id, userId));
    }

    public void updateActivity(Long id, CreateUpdateActivityCommand command) {
        UserId userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        activityRepository.findByIdAndOwnerId(id, userId)
            .map(a -> a.updateDetails(ActivityName.from(command.name), ActivityDescription.from(command.description)))
            .ifPresent(activityRepository::save);
    }

    public List<ActivityOverview> list() {
        UserId userId = this.authenticatedUserPort.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        return this.activityRepository.findAllByOwnerId(userId);
    }
}
