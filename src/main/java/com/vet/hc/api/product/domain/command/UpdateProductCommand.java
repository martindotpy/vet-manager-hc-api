package com.vet.hc.api.product.domain.command;

import lombok.Builder;
import lombok.Getter;

/**
 * Command to update a product.
 */
@Getter
@Builder
public class UpdateProductCommand {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
}
