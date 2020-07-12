package org.yeffrey.cheesecakespring.library.stories;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.library.core.ResourceNotFoundException;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.domain.ActivityDescription;
import org.yeffrey.cheesecakespring.library.domain.ActivityName;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.library.ports.ActivityRepositoryPort;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ActivityStories {
    private final LibraryStories libraryStories;
    private final ActivityRepositoryPort activityRepositoryPort;

    public ActivityStories(LibraryStories libraryStories,
                           ActivityRepositoryPort activityRepositoryPort) {
        this.libraryStories = libraryStories;
        this.activityRepositoryPort = activityRepositoryPort;
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Long addToLibrary(CreateUpdateActivityCommand command) {
        Library library = this.libraryStories.findForCurrentUser();
        Activity newActivity = Activity.from(library, ActivityName.from(command.name), ActivityDescription.from(command.description));
        return this.activityRepositoryPort.save(newActivity).getId();
    }

    @PreAuthorize("@policy.canManageActivity(authentication, #activityId)")
    public ActivityDetails findDetails(Long activityId) {
        return this.activityRepositoryPort.findDetailsById(activityId).orElseThrow(ResourceNotFoundException::new);
    }

    @PreAuthorize("isAuthenticated()")
    public List<ActivityOverview> findAllForCurrentUser() {
        Library library = this.libraryStories.findForCurrentUser();
        return this.activityRepositoryPort.findActivitiesByLibrary(library);
    }

    @PreAuthorize("@policy.canManageActivity(authentication, #activityId)")
    @Transactional
    public void updateInformation(Long activityId,
                                  CreateUpdateActivityCommand command) {
        Activity activity = this.activityRepositoryPort.findById(activityId).orElseThrow(ResourceNotFoundException::new);
        activity.updateDetails(ActivityName.from(command.name), ActivityDescription.from(command.description));
        this.activityRepositoryPort.save(activity);
    }
}
