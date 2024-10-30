package com.vet.hc.api.product.adapter.in.response;

import java.util.List;

import com.vet.hc.api.product.application.dto.CategoryDto;
import com.vet.hc.api.shared.domain.query.ContentResponse;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response for getting all categories.
 */
@Getter
@SuperBuilder
public class CategoriesResponse extends ContentResponse<List<CategoryDto>> {
}
