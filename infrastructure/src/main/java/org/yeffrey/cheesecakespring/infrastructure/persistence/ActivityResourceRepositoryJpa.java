package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.library.domain.ActivityResource;
import org.yeffrey.cheesecakespring.library.dto.ActivityResourceDetails;

import java.util.List;

@Repository
interface ActivityResourceRepositoryJpa extends JpaRepository<ActivityResource, Long> {
    List<ActivityResourceDetails> findAllByActivityId(Long activityId);
}
