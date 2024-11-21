package com.vet.hc.api.product.adapter.in.response;

import java.util.List;

import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.shared.adapter.in.response.PaginatedResponse;

import lombok.experimental.SuperBuilder;

/**
 * Paginated result for products.
 */
@SuperBuilder
public class PaginatedProductResponse extends PaginatedResponse<List<ProductDto>> {
}
