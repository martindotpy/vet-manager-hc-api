package com.vet.hc.api.sales.domain.model;

import com.vet.hc.api.client.domain.model.Client;
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
    private Client client;
    private User seller;
    private Bill bill;
}
