package org.yeffrey.cheesecakespring.features.activities.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.features.activities.domain.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration")
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class ActivityRepositoryTest {
    @Autowired
    ActivityRepository activityRepository;

    @Test
    public void testit() {
        Optional<ActivityDetails> details = activityRepository.findDetailsByIdAndOwnerId(1L,"user");
        Optional<Activity> entity = activityRepository.findByIdAndOwnerId(1L,"user");
        List<ActivityOverview> entities = activityRepository.findAllByOwnerId("user");
    }
}