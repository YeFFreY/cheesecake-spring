package org.yeffrey.cheesecakespring.library.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateUpdateResourceCommand {

    @NotBlank
    @Size(max = 355)
    public final String name;
    public final String description;
    @NotBlank
    public final String quantityUnit;

    @JsonCreator()
    public CreateUpdateResourceCommand(@JsonProperty("name") String name,
                                       @JsonProperty("description") String description,
                                       @JsonProperty("quantityUnit") String quantityUnit) {
        this.name = name;
        this.description = description;
        this.quantityUnit = quantityUnit;
    }

}
