package com.vet.hc.api.product.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Command to update a product.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
}
