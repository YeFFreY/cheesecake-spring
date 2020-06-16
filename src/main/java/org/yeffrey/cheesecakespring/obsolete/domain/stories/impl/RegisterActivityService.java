package org.yeffrey.cheesecakespring.obsolete.domain.stories.impl;

//@Service
class RegisterActivityService /*implements RegisterActivityStory*/ {
/*
    private final CreateActivityPort createActivityPort;
    private final AuthenticatedUserPort authenticatedUserPort;

    RegisterActivityService(CreateActivityPort createActivityPort, AuthenticatedUserPort authenticatedUserPort) {
        this.createActivityPort = createActivityPort;
        this.authenticatedUserPort = authenticatedUserPort;
    }

    @Override
    public Long registerActivity(RegisterActivityCommand command) {
        Activity activity = authenticatedUserPort.getAuthenticatedUserId()
            .map(u -> Activity.from(command.name, command.description, u))
            .orElseThrow(AccessDeniedException::new);

        return createActivityPort.save(activity).getId();
    }
*/
}
