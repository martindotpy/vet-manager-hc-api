package com.vet.hc.api.bill.productsale.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

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
@SQLDelete(sql = "UPDATE product_sale SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedProductSaleFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedProductSaleFilter", condition = "deleted = :isDeleted")
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

    @Builder.Default
    private boolean deleted = false;
}
