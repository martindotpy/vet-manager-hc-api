package com.vet.hc.api.bill.productsale.adapter.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.vet.hc.api.bill.productsale.adapter.out.persistence.entity.ProductSaleEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Hibernate repository for product sales.
 */
public class ProductSaleHibernateRepository implements HibernateRepository<ProductSaleEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all product sales.
     *
     * @return The list of product sales found
     */
    public List<ProductSaleEntity> findAll() {
        return findAll(entityManager, ProductSaleEntity.class);
    }

    /**
     * Finds a product sale by ID.
     *
     * @param clientId The product sale ID to search by.
     * @return The product sale found
     */
    public Optional<ProductSaleEntity> findById(Long clientId) {
        return findById(entityManager, ProductSaleEntity.class, clientId);
    }

    /**
     * Saves a product sale.
     *
     * @param productSaleEntity The product sale to save.
     * @return The saved product sale
     */
    @Transactional
    public ProductSaleEntity save(ProductSaleEntity productSaleEntity) {
        return save(entityManager, productSaleEntity);
    }

    /**
     * Deletes a product sale by ID.
     *
     * @param id The product sale ID to delete by
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, ProductSaleEntity.class, id);
    }
}
