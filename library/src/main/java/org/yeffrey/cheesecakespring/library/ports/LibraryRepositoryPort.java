package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.UserId;

import java.util.Optional;

public interface LibraryRepositoryPort {
    Optional<Library> findByOwnerId(UserId userId);

    Library save(Library library);
}
