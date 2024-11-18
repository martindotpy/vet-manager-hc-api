package com.vet.hc.api.product.adapter.in.response;

import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.shared.domain.query.Paginated;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedProduct extends Paginated<ProductDto> {
}
