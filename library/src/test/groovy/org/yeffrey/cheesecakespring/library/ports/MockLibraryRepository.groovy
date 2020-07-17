package org.yeffrey.cheesecakespring.library.ports

import org.yeffrey.cheesecakespring.common.domain.UserId
import org.yeffrey.cheesecakespring.library.domain.Library

import java.util.concurrent.atomic.AtomicLong

class MockLibraryRepository implements LibraryRepositoryPort {
    AtomicLong seq = new AtomicLong()
    Map<UserId, Library> db = [:]

    @Override
    Optional<Library> findByOwnerId(UserId userId) {
        return Optional.ofNullable(db[userId])
    }

    @Override
    Library save(Library library) {
        library.id = seq.getAndIncrement()
        db[library.ownerId] = library
        return library
    }
}
