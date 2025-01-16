package com.vet.hc.api.product.category.adapter.out.persistence.entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Category entity.
 */
@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedCategoryFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedCategoryFilter", condition = "deleted = :isDeleted")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Long id;

    @Column(columnDefinition = "VARCHAR(12)", nullable = false)
    private String name;

    @Builder.Default
    private boolean deleted = false;
}
