package com.vet.hc.api.product.category.application.response;

import java.util.List;

import com.vet.hc.api.product.category.domain.dto.CategoryDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response for getting all categories.
 */
@Getter
@SuperBuilder
public class CategoriesResponse extends ContentResponse<List<CategoryDto>> {
}
