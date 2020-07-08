package org.yeffrey.cheesecakespring.library.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public class AdjustActivityResourceQuantityCommand {
    @Min(1)
    public final int quantity;

    @JsonCreator()
    public AdjustActivityResourceQuantityCommand(@JsonProperty("quantity") int quantity) {
        this.quantity = quantity;
    }

}
