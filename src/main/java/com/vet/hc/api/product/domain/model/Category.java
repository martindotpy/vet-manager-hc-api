package com.vet.hc.api.product.domain.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a category.
 */
@Getter
@Builder
public class Category {
    private Long id;

    private String name;

    private List<Product> products;
}
