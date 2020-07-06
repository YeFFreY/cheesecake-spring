package org.yeffrey.cheesecakespring.activities;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.activities.core.AccessDeniedException;
import org.yeffrey.cheesecakespring.activities.domain.Activity;
import org.yeffrey.cheesecakespring.activities.domain.ActivityDescription;
import org.yeffrey.cheesecakespring.activities.domain.ActivityName;
import org.yeffrey.cheesecakespring.activities.domain.UserId;
import org.yeffrey.cheesecakespring.activities.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.activities.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.activities.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.activities.ports.ActivityRepository;
import org.yeffrey.cheesecakespring.activities.ports.AuthenticatedUserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ActivityStories {

    private final ActivityRepository activityRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ActivityStories(ActivityRepository activityRepository, AuthenticatedUserService authenticatedUserService) {
        this.activityRepository = activityRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional
    public Long registerActivity(CreateUpdateActivityCommand command) {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        Activity newActivity = Activity.from(ActivityName.from(command.name), ActivityDescription.from(command.description), userId);
        return this.activityRepository.save(newActivity).getId();
    }

    public Optional<ActivityDetails> findById(Long id) {
        return this.authenticatedUserService.getAuthenticatedUserId()
            .flatMap(userId -> activityRepository.findDetailsByIdAndOwnerId(id, userId));
    }

    @Transactional
    public void updateActivity(Long id, CreateUpdateActivityCommand command) {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);

        // TODO ok it seems that I will not retrieve entity using the "owner" as a criteria because this where clause to update an activity might be problematic if more than owner can update this activity
        // TODO I will have to to split retrieval and update so I can add @Preauthorize and PostAuthorize to manage "correctly" the authorization
        //https://stackoverflow.com/questions/16164615/preauthorize-with-haspermission-executes-code-twice
        // page  328 : https://books.google.be/books?id=L-U5DwAAQBAJ&pg=PA329&lpg=PA329&dq=preauthorize+spring+only+id+?&source=bl&ots=SC4PFXRCF5&sig=ACfU3U2Fi9wfb25JNAZ6X3LpB5k96yoKhw&hl=en&sa=X&ved=2ahUKEwjcmNePy7bqAhXN16QKHbOUDvIQ6AEwDXoECAsQAQ#v=onepage&q=preauthorize%20spring%20only%20id%20%3F&f=false
        activityRepository.findByIdAndOwnerId(id, userId)
            .map(a -> a.updateDetails(ActivityName.from(command.name), ActivityDescription.from(command.description)))
            .ifPresent(activityRepository::save);
    }

    public List<ActivityOverview> list() {
        UserId userId = this.authenticatedUserService.getAuthenticatedUserId().orElseThrow(AccessDeniedException::new);
        return this.activityRepository.findAllByOwnerId(userId);
    }
}
