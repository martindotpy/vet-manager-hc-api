package com.vet.hc.api.sales.core.domain.model;

import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.sales.bill.domain.model.Bill;
import com.vet.hc.api.user.core.domain.model.User;

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
