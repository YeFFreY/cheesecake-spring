package org.yeffrey.cheesecakespring.activities.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddResourceToActivityCommand {
    @Min(1)
    public final int quantity;

    @NotNull
    public final Long resourceId;

    @JsonCreator()
    public AddResourceToActivityCommand(@JsonProperty("resourceId") Long resourceId, @JsonProperty("quantity") int quantity) {
        this.resourceId = resourceId;
        this.quantity = quantity;
    }
}
