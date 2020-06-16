package org.yeffrey.cheesecakespring.features.activities.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateUpdateActivityCommand {

    @NotBlank
    @Size(max = 355)
    public final String name;
    public final String description;

    @JsonCreator()
    public CreateUpdateActivityCommand(@JsonProperty("name") String name, @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

}
