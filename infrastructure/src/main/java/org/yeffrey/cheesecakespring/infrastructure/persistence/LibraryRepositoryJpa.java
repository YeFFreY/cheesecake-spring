package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.UserId;

import java.util.Optional;

@Repository
interface LibraryRepositoryJpa extends JpaRepository<Library, Long> {
    Optional<Library> findByOwnerId(UserId userId);
}
