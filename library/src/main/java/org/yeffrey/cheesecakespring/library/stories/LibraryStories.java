package org.yeffrey.cheesecakespring.library.stories;

import org.springframework.context.event.EventListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.yeffrey.cheesecakespring.common.domain.UserId;
import org.yeffrey.cheesecakespring.common.event.UserRegisteredEvent;
import org.yeffrey.cheesecakespring.library.core.AccessDeniedException;
import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.exception.LibraryNotFoundException;
import org.yeffrey.cheesecakespring.library.ports.AuthenticatedUserPort;
import org.yeffrey.cheesecakespring.library.ports.LibraryRepositoryPort;

@Service
@Transactional(readOnly = true)
public class LibraryStories {
    private final AuthenticatedUserPort authenticatedUserPort;
    private final LibraryRepositoryPort libraryRepositoryPort;

    public LibraryStories(AuthenticatedUserPort authenticatedUserPort,
                          LibraryRepositoryPort libraryRepositoryPort) {
        this.authenticatedUserPort = authenticatedUserPort;
        this.libraryRepositoryPort = libraryRepositoryPort;
    }

    @PreAuthorize("isAuthenticated()")
    public Library findForCurrentUser() {
        return this.authenticatedUserPort.findAuthenticatedUserId()
            .flatMap(this.libraryRepositoryPort::findByOwnerId)
            .orElseThrow(LibraryNotFoundException::new);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Long createLibraryForCurrentUser() {
        try {
            return this.findForCurrentUser().getId();
        } catch (LibraryNotFoundException exception) {
            return this.authenticatedUserPort.findAuthenticatedUserId()
                .map(Library::from)
                .map(this.libraryRepositoryPort::save)
                .map(Library::getId)
                .orElseThrow(AccessDeniedException::new);
        }
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createLibraryOn(UserRegisteredEvent event) {
        try {
            this.findForCurrentUser();
        } catch (LibraryNotFoundException exception) {
            Library library = Library.from(UserId.from(event.getUsername()));
            this.libraryRepositoryPort.save(library).getId();
        }
    }
}
