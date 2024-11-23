package com.vet.hc.api.product.core.application.response;

import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated result for products.
 */
@SuperBuilder
public class PaginatedProductResponse extends PaginatedResponse<ProductDto> {
}
