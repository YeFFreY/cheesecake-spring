package org.yeffrey.cheesecakespring.obsolete.domain.stories;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public interface RegisterActivityStory {
    Long registerActivity(RegisterActivityCommand command);

    class RegisterActivityCommand {

        @NotBlank
        @Size(max = 355)
        public final String name;
        public final String description;

        @JsonCreator()
        public RegisterActivityCommand(@JsonProperty("name") String name, @JsonProperty("description") String description) {
            this.name = name;
            this.description = description;
        }

    }
}
