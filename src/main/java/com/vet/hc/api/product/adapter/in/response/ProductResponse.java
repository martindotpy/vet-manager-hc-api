package com.vet.hc.api.product.adapter.in.response;

import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.shared.domain.query.Response;

import lombok.experimental.SuperBuilder;

/**
 * Response object for a product.
 */
@SuperBuilder
public class ProductResponse extends Response<ProductDto> {
}
