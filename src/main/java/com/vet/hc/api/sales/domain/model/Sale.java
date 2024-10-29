package com.vet.hc.api.sales.domain.model;

import com.vet.hc.api.client.domain.model.Client;
import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.user.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a sale.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
