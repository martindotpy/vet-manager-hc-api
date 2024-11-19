package com.vet.hc.api.product.application.port.in;

import java.util.List;

import com.vet.hc.api.product.application.dto.CategoryDto;

/**
 * Port for finding a category.
 */
public interface FindCategoryPort {
    /**
     * Match a category with the given criteria.
     *
     * @return the result of the operation
     */
    List<CategoryDto> findAll();
}
