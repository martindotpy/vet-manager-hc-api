package com.vet.hc.api.product.adapter.out.persistance.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.vet.hc.api.product.adapter.out.persistance.entity.CategoryEntity;
import com.vet.hc.api.product.adapter.out.persistance.entity.ProductEntity;
import com.vet.hc.api.shared.adapter.out.repository.HibernateRepository;
import com.vet.hc.api.shared.adapter.out.repository.PaginatedHibernateRepository;
import com.vet.hc.api.shared.application.util.StringUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

/**
 * Repository for products using Hibernate.
 */
public class ProductHibernateRepository extends PaginatedHibernateRepository<ProductEntity>
        implements HibernateRepository<ProductEntity, Long> {
    @PersistenceContext(unitName = "database")
    private EntityManager entityManager;

    /**
     * Finds all products.
     *
     * @return The list of products found.
     */
    public List<ProductEntity> findAll() {
        return findAll(entityManager, ProductEntity.class);
    }

    /**
     * Finds a product by ID.
     *
     * @param productId The product ID to search by.
     * @return The product found.
     */
    public Optional<ProductEntity> findById(Long productId) {
        return findById(entityManager, ProductEntity.class, productId);
    }

    /**
     * Finds a product by email.
     *
     * @param criteria The criteria to search by.
     * @return The product found
     */
    public PaginatedResponse<List<ProductEntity>> match(Criteria criteria, Iterable<Integer> categoryIds) {
        CriteriaBuilder criteriaCountBuilder = entityManager.getCriteriaBuilder();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> root = criteriaQuery.from(ProductEntity.class);
        CriteriaQuery<Long> countQuery = criteriaCountBuilder.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        countQuery.select(criteriaCountBuilder.count(countRoot));

        List<Predicate> predicatesCount = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        for (Filter filter : criteria.getFilters()) {
            addFilter(filter, criteriaBuilder, root, predicates);
            addFilter(filter, criteriaBuilder, countRoot, predicatesCount);
        }

        if (categoryIds != null && categoryIds.iterator().hasNext()) {
            Join<ProductEntity, CategoryEntity> categoryJoin = root.join("categories");
            Join<ProductEntity, CategoryEntity> countCategoryJoin = countRoot.join("categories");
            predicates.add(categoryJoin.get("id").in(Arrays.asList(categoryIds)));
            predicatesCount.add(countCategoryJoin.get("id").in(Arrays.asList(categoryIds)));
        }

        countQuery.where(predicatesCount.toArray(Predicate[]::new));
        long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        criteriaQuery.select(root).where(predicates.toArray(Predicate[]::new));

        switch (criteria.getOrder().getType()) {
            case ASC -> criteriaQuery
                    .orderBy(criteriaBuilder.asc(root.get(StringUtils.toCamelCase(criteria.getOrder().getField()))));
            case DESC -> criteriaQuery
                    .orderBy(criteriaBuilder.desc(root.get(StringUtils.toCamelCase(criteria.getOrder().getField()))));
            case NONE -> {
                // Do nothing
            }
        }

        long page = criteria.getPage();
        long size = criteria.getSize();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        List<ProductEntity> clients = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();

        return PaginatedResponse.<List<ProductEntity>>builder()
                .message("Products found")
                .content(clients)
                .page((int) page)
                .size((int) size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    /**
     * Saves a product.
     *
     * @param productEntity The product to save.
     * @return The saved product
     */
    @Transactional
    public ProductEntity save(ProductEntity productEntity) {
        return save(entityManager, productEntity);
    }

    /**
     * Deletes a product.
     *
     * @param id The ID of the product to delete.
     */
    @Transactional
    public void deleteById(Long id) {
        deleteById(entityManager, ProductEntity.class, id);
    }
}
