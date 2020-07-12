package org.yeffrey.cheesecakespring.library.ports;

import org.yeffrey.cheesecakespring.library.domain.Library;
import org.yeffrey.cheesecakespring.library.domain.Resource;
import org.yeffrey.cheesecakespring.library.domain.UserId;
import org.yeffrey.cheesecakespring.library.dto.ResourceDetails;
import org.yeffrey.cheesecakespring.library.dto.ResourceOverview;

import java.util.List;
import java.util.Optional;

public interface ResourceRepositoryPort {
    boolean resourceBelongsToUserLibrary(Long resourceId,
                                         UserId userId);

    Resource save(Resource resource);

    Optional<ResourceDetails> findDetailsById(Long id);

    List<ResourceOverview> findResourcesByLibrary(Library library);

    Optional<Resource> findById(Long id);
}
