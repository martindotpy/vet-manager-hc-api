package com.vet.hc.api.shared.adapter.out.repository;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vet.hc.api.shared.application.util.StringUtils;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.query.Paginated;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

/**
 * Repository for paginated entities using Hibernate.
 */
@Slf4j
public abstract class PaginatedHibernateRepository<T> {
    @Autowired
    protected EntityManager entityManager;

    /**
     * Matches the criteria with the entity manager and the class.
     *
     * @param criteria The criteria to match by.
     * @param clazz    The class to match by. Must be an entity.
     * @return The paginated response
     * @throws IllegalArgumentException If the class is not an entity.
     */
    public Paginated<T> match(Criteria criteria, Class<T> clazz) {
        checkArgument(clazz.getSimpleName().contains("Entity"), "The class {} must be an entity",
                clazz.getCanonicalName());

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

        return Paginated.<T>builder()
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
    protected void addFilter(Filter filter, CriteriaBuilder criteriaBuilder, Root<T> root, List<Predicate> predicates) {
        if (filter.getValue() == null || filter.getValue().isBlank()) {
            return;
        }

        // Divide el campo en partes si contiene '.'
        String[] fieldParts = filter.getField().split("\\.");
        Path<?> path = root;

        for (String part : fieldParts) {
            path = path.get(part);
        }

        // Generar el predicado basado en el operador del filtro
        Predicate predicate = switch (filter.getOperator()) {
            case LIKE -> criteriaBuilder.like(path.as(String.class), "%" + filter.getValue() + "%");
            default -> throw new IllegalArgumentException("Unsupported filter operator: " + filter.getOperator());
        };

        predicates.add(predicate);
    }

}
