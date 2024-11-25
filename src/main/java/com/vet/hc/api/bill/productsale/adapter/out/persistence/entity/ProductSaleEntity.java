package com.vet.hc.api.bill.productsale.adapter.out.persistence.entity;

import com.vet.hc.api.bill.core.adapter.out.persistence.entity.BillEntity;
import com.vet.hc.api.product.core.adapter.out.persistence.entity.ProductEntity;
import com.vet.hc.api.user.core.adapter.out.persistence.model.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Product sale entity.
 */
@Entity
@Table(name = "product_sale")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Integer discount;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity product;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity seller;
    @ManyToOne
    private BillEntity bill;
}
