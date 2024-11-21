package com.vet.hc.api.product.adapter.in.response;

import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response for getting a category.
 */
@Getter
@SuperBuilder
public class CategoryResponse extends ContentResponse<CategoryDto> {
}
