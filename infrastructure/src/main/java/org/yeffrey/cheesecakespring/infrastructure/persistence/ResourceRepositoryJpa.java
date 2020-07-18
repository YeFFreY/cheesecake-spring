package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.Resource;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

@Repository
interface ResourceRepositoryJpa extends JpaRepository<Resource, Long> {
    Optional<ResourceDetails> findDetailsById(Long id);

    List<ResourceOverview> findOverviewByLibrary(Library library);

    @Query("select case when count(r) > 0 then true else false end from Resource r where r.id = :resourceId and r.library.ownerId = :userId")
    boolean resourceBelongsToUserLibrary(@Param("resourceId") Long resourceId,
                                         @Param("userId") UserId userId);
}
