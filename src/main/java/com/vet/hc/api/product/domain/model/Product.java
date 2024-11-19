package com.vet.hc.api.product.domain.model;

import java.time.LocalDateTime;
import java.util.List;

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
