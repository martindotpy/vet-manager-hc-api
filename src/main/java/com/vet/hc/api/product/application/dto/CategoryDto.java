package com.vet.hc.api.product.application.dto;

import java.util.List;

import com.vet.hc.api.product.domain.model.Product;

import lombok.Builder;
import lombok.Getter;

/**
 * Category dto.
 */
@Getter
@Builder
public class CategoryDto {
    private Long id;

    private String name;

    private List<Product> products;
}
