package com.vet.hc.api.bill.productsale.domain.dto;

import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.user.core.domain.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for product sale.
 *
 * @see com.vet.hc.api.bill.productsale.domain.model.ProductSale
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProductSaleDto {
    private Long id;

    private Double price;
    private Integer discount;
    private Integer quantity;

    private ProductDto product;
    private UserDto seller;
}
