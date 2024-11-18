package com.vet.hc.api.product.domain.command;

import java.util.Set;

import com.vet.hc.api.product.domain.model.Category;
import com.vet.hc.api.shared.domain.payload.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Command to create a product.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCommand implements Payload {
    private String name;
    private Double price;
    private String description;
    private Integer quantity;

    private Set<Category> categories;
}
