package com.vet.hc.api.shared.adapter.out.repository;

import java.util.ArrayList;
import java.util.List;

import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.query.PaginatedResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PaginatedHibernateRepository<T> {
    default PaginatedResponse<List<T>> match(Criteria criteria, EntityManager entityManager, Class<T> clazz) {
        if (!clazz.getCanonicalName().contains("Entity")) {
            throw new IllegalArgumentException("The class must be an entity");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(clazz);
        countQuery.select(criteriaBuilder.count(countRoot));

        List<Predicate> predicates = new ArrayList<>();
        for (Filter filter : criteria.getFilters()) {
            if (filter.getValue() != null && !filter.getValue().isBlank()) {
                Predicate predicate = criteriaBuilder.like(countRoot.get(filter.getField()),
                        "%" + filter.getValue() + "%");
                predicates.add(predicate);
            }
        }
        countQuery.where(predicates.toArray(new Predicate[0]));
        long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));

        switch (criteria.getOrder().getType()) {
            case ASC -> criteriaQuery.orderBy(criteriaBuilder.asc(root.get(criteria.getOrder().getField())));
            case DESC -> criteriaQuery.orderBy(criteriaBuilder.desc(root.get(criteria.getOrder().getField())));
            case NONE -> {
            }
        }

        long page = criteria.getPage();
        long size = criteria.getSize();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        List<T> clients = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) ((page - 1) * size))
                .setMaxResults((int) size)
                .getResultList();

        String className = clazz.getSimpleName();
        className = className.substring(0, className.length() - 6).toLowerCase() + "s";

        return PaginatedResponse.<List<T>>builder()
                .message(className + " found")
                .content(clients)
                .page((int) page)
                .size((int) size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }
}
