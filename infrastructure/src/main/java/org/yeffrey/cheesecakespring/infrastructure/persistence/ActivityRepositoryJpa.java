package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.library.domain.Activity;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.UserId;
import org.yeffrey.cheesecakespring.library.dto.ActivityDetails;
import org.yeffrey.cheesecakespring.library.dto.ActivityOverview;

import java.util.List;
import java.util.Optional;

@Repository
interface ActivityRepositoryJpa extends JpaRepository<Activity, Long> {

    Optional<ActivityDetails> findDetailsById(Long id);

    boolean existsById(Long id);

    List<ActivityOverview> findOverviewByLibrary(Library library);

    @Query("select case when count(a) > 0 then true else false end from Activity a where a.id = :activityId and a.library.ownerId = :userId")
    boolean activityBelongsToUserLibrary(Long activityId,
                                         UserId userId);
}
