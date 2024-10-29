package com.vet.hc.api.shared.adapter.out.repository;

import java.util.ArrayList;
import java.util.List;

import com.vet.hc.api.shared.application.util.StringUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Repository for paginated entities using Hibernate.
 */
public abstract class PaginatedHibernateRepository<T> {
    /**
     * Matches the criteria with the entity manager and the class.
     *
     * @param criteria      The criteria to match by.
     * @param entityManager The entity manager to use.
     * @param clazz         The class to match by. Must be an entity.
     * @return The paginated response
     * @throws IllegalArgumentException If the class is not an entity.
     */
    protected PaginatedResponse<List<T>> match(Criteria criteria, EntityManager entityManager, Class<T> clazz) {
        if (!clazz.getCanonicalName().contains("Entity")) {
            throw new IllegalArgumentException("The class must be an entity");
        }

        CriteriaBuilder criteriaCountBuilder = entityManager.getCriteriaBuilder();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        CriteriaQuery<Long> countQuery = criteriaCountBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(clazz);
        countQuery.select(criteriaCountBuilder.count(countRoot));

        List<Predicate> predicatesCount = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        for (Filter filter : criteria.getFilters()) {
            addFilter(filter, criteriaBuilder, root, predicates);
            addFilter(filter, criteriaBuilder, countRoot, predicatesCount);
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

        List<T> clients = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();

        String className = getClassName(clazz);

        return PaginatedResponse.<List<T>>builder()
                .message(className + " found")
                .content(clients)
                .page((int) page)
                .size((int) size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    /**
     * Adds a filter to the query.
     *
     * @param filter          The filter to add.
     * @param criteriaBuilder The criteria builder to use.
     * @param root            The root to use.
     * @param predicates      The predicates to add to.
     */
    private void addFilter(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> root, List<Predicate> predicates) {
        if (filter.getValue() == null || filter.getValue().isBlank())
            return;

        Predicate predicate = switch (filter.getOperator()) {
            case CONTAINS ->
                criteriaBuilder.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
        };

        predicates.add(predicate);
    }

    /**
     * Gets the class name.
     *
     * @param clazz The class to get the name from.
     * @return The class name
     */
    private String getClassName(Class<T> clazz) {
        String className = clazz.getSimpleName();
        return className.substring(0, className.length() - 6).toLowerCase() + "s";
    }
}
