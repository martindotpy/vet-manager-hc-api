package com.vet.hc.api.product.application.dto;

import java.util.List;

import com.vet.hc.api.product.domain.model.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Category dto.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    private String name;

    private List<Product> products;
}
