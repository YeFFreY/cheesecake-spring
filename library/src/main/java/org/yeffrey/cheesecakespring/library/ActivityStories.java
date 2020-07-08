package org.yeffrey.cheesecakespring.library;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.domain.ActivityDescription;
import org.yeffrey.cheesecakespring.library.domain.ActivityName;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;
import org.yeffrey.cheesecakespring.library.dto.CreateUpdateActivityCommand;
import org.yeffrey.cheesecakespring.library.ports.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ActivityStories {

    private final ActivityRepository activityRepository;

    public ActivityStories(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Transactional
    public Long registerActivity(CreateUpdateActivityCommand command) {
        Activity newActivity = Activity.from(ActivityName.from(command.name), ActivityDescription.from(command.description));
        return this.activityRepository.save(newActivity).getId();
    }

    public Optional<ActivityDetails> findById(Long id) {
        return activityRepository.findDetailsById(id);
    }

    @PreAuthorize("hasPermission(#id, 'activity', 'update')")
    @Transactional
    public void updateActivity(Long id, CreateUpdateActivityCommand command) {

        // TODO ok it seems that I will not retrieve entity using the "owner" as a criteria because this where clause to update an activity might be problematic if more than owner can update this activity
        // TODO I will have to to split retrieval and update so I can add @Preauthorize and PostAuthorize to manage "correctly" the authorization
        //https://stackoverflow.com/questions/16164615/preauthorize-with-haspermission-executes-code-twice
        // page  328 : https://books.google.be/books?id=L-U5DwAAQBAJ&pg=PA329&lpg=PA329&dq=preauthorize+spring+only+id+?&source=bl&ots=SC4PFXRCF5&sig=ACfU3U2Fi9wfb25JNAZ6X3LpB5k96yoKhw&hl=en&sa=X&ved=2ahUKEwjcmNePy7bqAhXN16QKHbOUDvIQ6AEwDXoECAsQAQ#v=onepage&q=preauthorize%20spring%20only%20id%20%3F&f=false
        activityRepository.findById(id)
            .map(a -> a.updateDetails(ActivityName.from(command.name), ActivityDescription.from(command.description)))
            .ifPresent(activityRepository::save);
    }

    public List<ActivityOverview> list() {
        return this.activityRepository.findAll();
    }
}
