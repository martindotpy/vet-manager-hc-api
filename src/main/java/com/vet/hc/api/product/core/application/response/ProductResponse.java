package com.vet.hc.api.product.core.application.response;

import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response object for a product.
 */
@SuperBuilder
public class ProductResponse extends ContentResponse<ProductDto> {
}
