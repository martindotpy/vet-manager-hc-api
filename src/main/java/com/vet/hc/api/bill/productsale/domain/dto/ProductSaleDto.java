package com.vet.hc.api.bill.productsale.domain.dto;

import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.shared.domain.excel.ColumnClassName;
import com.vet.hc.api.shared.domain.excel.ColumnPropertyName;
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
@ColumnClassName("Venta de producto")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProductSaleDto {
    @ColumnPropertyName("Id")
    private Long id;

    @ColumnPropertyName("Precio")
    private Double price;
    @ColumnPropertyName("Descuento")
    private Integer discount;
    @ColumnPropertyName("Cantidad")
    private Integer quantity;

    @ColumnPropertyName("Producto")
    private ProductDto product;
    @ColumnPropertyName("Vendedor")
    private UserDto seller;
}
