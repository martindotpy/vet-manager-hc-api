package com.vet.hc.api.product.domain.command;

import java.util.Set;

import com.vet.hc.api.product.domain.model.Category;

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
    private Double price;
    private Integer quantity;

    private Set<Category> categories;
}
