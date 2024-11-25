package com.vet.hc.api.bill.productsale.adapter.in.response;

import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.shared.adapter.in.response.ContentResponse;

import lombok.experimental.SuperBuilder;

/**
 * Response for {@link ProductSaleDto}.
 */
@SuperBuilder
public final class ProductSaleResponse extends ContentResponse<ProductSaleDto> {
}
