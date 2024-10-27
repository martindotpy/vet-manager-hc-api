package com.vet.hc.api.product.adapter.in.response;

import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated result for products.
 */
@SuperBuilder
public class PaginatedProductResponse extends PaginatedResponse<ProductDto> {
}
