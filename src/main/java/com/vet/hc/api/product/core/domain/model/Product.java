package com.vet.hc.api.product.core.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.vet.hc.api.product.category.domain.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a product.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;

    private String name;
    private Double price;
    private String description;
    private Integer quantity;
    private LocalDateTime updatedAt;

    private List<Category> categories;
}
