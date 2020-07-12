package org.yeffrey.cheesecakespring.infrastructure.web.rest.activities;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeffrey.cheesecakespring.infrastructure.web.rest.EntityId;
import org.yeffrey.cheesecakespring.library.stories.LibraryStories;

@RestController
@RequestMapping("/api/libraries")
public class LibrariesController {
    private final LibraryStories libraryStories;

    public LibrariesController(LibraryStories libraryStories) {
        this.libraryStories = libraryStories;
    }

    @PostMapping
    public ResponseEntity<EntityId> create() {
        Long id = this.libraryStories.createLibraryForCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityId.from(id));
    }

}
