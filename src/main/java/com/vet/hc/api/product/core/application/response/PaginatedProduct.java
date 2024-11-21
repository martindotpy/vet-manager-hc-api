package com.vet.hc.api.product.core.application.response;

import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.shared.domain.query.Paginated;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public final class PaginatedProduct extends Paginated<ProductDto> {
}
