package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.library.domain.Library;

import java.util.Optional;

public interface LibraryRepositoryPort {
    Optional<Library> findByOwnerId(UserId userId);

    Library save(Library library);
}
