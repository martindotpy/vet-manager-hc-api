package com.vet.hc.api.bill.productsale.domain.model;

import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a product sale.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSale {
    private Long id;

    private Double price;
    private Integer discount;
    private Integer quantity;

    private Product product;
    private User seller;
    @Nullable
    private Bill bill;
}
