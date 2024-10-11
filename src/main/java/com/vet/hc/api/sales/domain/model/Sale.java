package com.vet.hc.api.sales.domain.model;

import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.user.domain.model.User;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a sale.
 */
@Getter
@Builder
public class Sale {
    private Long id;

    private Double price;
    private Double discount;
    private Integer quantity;

    private Product soldProduct;
    // Client
    private User seller;
    // Bill
}
