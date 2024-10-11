package com.vet.hc.api.product.domain.model;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a product.
 */
@Getter
@Builder
public class Product {
    private Long id;

    private String name;
    private Double price;
    private String description;
    private Integer quantity;

    private Instant updatedAt;
}
