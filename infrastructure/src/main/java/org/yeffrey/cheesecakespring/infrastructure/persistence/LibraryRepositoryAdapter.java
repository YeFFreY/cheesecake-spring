package org.yeffrey.cheesecakespring.infrastructure.persistence;

import org.springframework.stereotype.Component;
import org.yeffrey.cheesecakespring.infrastructure.core.PortAdapter;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.UserId;
import org.yeffrey.cheesecakespring.library.ports.LibraryRepositoryPort;

import java.util.Optional;

@PortAdapter
@Component
public class LibraryRepositoryAdapter implements LibraryRepositoryPort {
    private final LibraryRepositoryJpa libraryRepositoryJpa;

    public LibraryRepositoryAdapter(LibraryRepositoryJpa libraryRepositoryJpa) {
        this.libraryRepositoryJpa = libraryRepositoryJpa;
    }

    @Override
    public Optional<Library> findByOwnerId(UserId userId) {
        return this.libraryRepositoryJpa.findByOwnerId(userId);
    }

    @Override
    public Library save(Library library) {
        return this.libraryRepositoryJpa.save(library);
    }
}
