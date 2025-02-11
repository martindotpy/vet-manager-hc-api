package com.vet.hc.api.shared.adapter.out.persistence;

import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightRed;
import static com.vet.hc.api.shared.adapter.in.util.AnsiShortcuts.fgBrightYellow;
import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vet.hc.api.shared.application.mapper.BasicMapper;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.FilterOperator;
import com.vet.hc.api.shared.domain.criteria.LogicalFilter;
import com.vet.hc.api.shared.domain.criteria.OrderType;
import com.vet.hc.api.shared.domain.criteria.OrderedCriteria;
import com.vet.hc.api.shared.domain.criteria.PaginatedCriteria;
import com.vet.hc.api.shared.domain.criteria.ValueFilter;
import com.vet.hc.api.shared.domain.failure.ExceptionFailureHandler;
import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.failure.GenericFailure;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.result.Result;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

/**
 * Criteria entity persistence adapter.
 */
@Slf4j
public abstract class CriteriaEntityPersistenceAdapter<E, ID, DTO, F extends Failure, R extends JpaRepository<E, ID>>
        extends EntityPersistenceAdapter<E, ID, DTO, F, R> {

    public CriteriaEntityPersistenceAdapter(
            R repository,
            ExceptionFailureHandler<F> exceptionFailureHandler,
            BasicMapper<E, DTO, F> mapper) {
        super(repository, exceptionFailureHandler, mapper);
    }

    public Result<E, F> findBy(Criteria criteria) {
        try {
            return ok(entityManager.createQuery(createQuery(criteria, entityClass)).getSingleResult());
        } catch (NoResultException e) {
            return failure(mapper.toFailure(GenericFailure.NOT_FOUND));
        } catch (Exception e) {
            return failure(handleException(e));
        }
    }

    public Result<List<E>, F> findAllBy(OrderedCriteria orderedCriteria) {
        try {
            return ok(entityManager.createQuery(createQuery(orderedCriteria)).getResultList());
        } catch (Exception e) {
            return failure(handleException(e));
        }
    }

    public Long countBy(Criteria criteria) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            Root<E> root = countQuery.from(entityClass);
            countQuery.select(criteriaBuilder.count(root));

            applyCriteria(criteria, criteriaBuilder, countQuery, root);

            return entityManager.createQuery(countQuery).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Result<Paginated<E>, F> findPaginatedBy(PaginatedCriteria criteria) {
        try {
            long totalElements = countBy(criteria);
            int page = criteria.getPage();
            int size = criteria.getSize();
            int totalPages = (int) Math.ceil((double) totalElements / size);

            List<E> entities = entityManager.createQuery(createQuery(criteria))
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();

            return ok(Paginated.<E>builder()
                    .content(entities)
                    .page(page)
                    .size(size)
                    .totalElements(totalElements)
                    .totalPages(totalPages)
                    .build());
        } catch (Exception e) {
            log.error("Error finding paginated entities");

            return failure(handleException(e));
        }
    }

    public Result<Integer, F> updateBy(Criteria criteria, FieldUpdate necessaryFieldUpdate,
            FieldUpdate... fieldUpdates) {
        Objects.requireNonNull(necessaryFieldUpdate, "Necessary field update is required");

        for (FieldUpdate fieldUpdate : fieldUpdates) {
            Objects.requireNonNull(fieldUpdate, "Field update is required");
        }

        FieldUpdate[] allFieldUpdates = new FieldUpdate[fieldUpdates.length + 1];
        allFieldUpdates[0] = necessaryFieldUpdate;
        System.arraycopy(fieldUpdates, 0, allFieldUpdates, 1, fieldUpdates.length);

        try {
            CriteriaUpdate<E> update = buildUpdateQuery(criteria, allFieldUpdates);
            int updatedCount = entityManager.createQuery(update).executeUpdate();
            log.debug("Updated {} entities", updatedCount);

            return ok(updatedCount);
        } catch (Exception e) {
            return failure(handleException(e));
        }
    }

    private <T> CriteriaQuery<T> createQuery(Criteria criteria, Class<T> resultType) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(resultType);
        Root<E> root = query.from(entityClass);
        applyCriteria(criteria, cb, query, root);
        return query;
    }

    private CriteriaQuery<E> createQuery(OrderedCriteria criteria) {
        CriteriaQuery<E> query = createQuery(criteria, entityClass);
        applyOrder(criteria, query);
        return query;
    }

    private <T> void applyCriteria(Criteria criteria, CriteriaBuilder cb, CriteriaQuery<T> query, Root<E> root) {
        List<Predicate> predicates = buildPredicates(criteria.getFilters(), cb, root);
        query.where(predicates.toArray(Predicate[]::new));
    }

    private List<Predicate> buildPredicates(Collection<Filter> filters, CriteriaBuilder cb, Root<E> root) {
        List<Predicate> predicates = new CopyOnWriteArrayList<>();
        for (Filter filter : filters) {
            buildPredicate(filter, cb, root, predicates);
        }
        return predicates;
    }

    private void buildPredicate(Filter filter, CriteriaBuilder cb, Root<E> root, List<Predicate> predicates) {
        switch (filter) {
            case LogicalFilter lf -> handleLogicalFilter(lf, cb, root, predicates);
            case ValueFilter vf -> handleValueFilter(vf, cb, root, predicates);
            default -> throw new UnsupportedOperationException("Unsupported filter type: " + filter.getClass());
        }
    }

    private void handleLogicalFilter(LogicalFilter filter, CriteriaBuilder cb, Root<E> root,
            List<Predicate> predicates) {
        List<Predicate> logicalPredicates = new CopyOnWriteArrayList<>();
        Arrays.stream(filter.getFilters()).forEach(f -> buildPredicate(f, cb, root, logicalPredicates));

        Predicate combined = switch (filter.getLogicalOperator()) {
            case AND -> cb.and(logicalPredicates.toArray(Predicate[]::new));
            case OR -> cb.or(logicalPredicates.toArray(Predicate[]::new));
            case NOT -> cb.not(cb.and(logicalPredicates.toArray(Predicate[]::new)));
        };

        predicates.add(combined);
    }

    private void handleValueFilter(ValueFilter filter, CriteriaBuilder cb, Root<E> root, List<Predicate> predicates) {
        if (filter.getValue() == null && filter.getFilterOperator() != FilterOperator.IS_NULL) {
            log.warn("Null value for filter: {} - {}",
                    fgBrightYellow(filter.getField()),
                    fgBrightYellow(filter.getFilterOperator()));
            return;
        }

        if (filter.getValue() instanceof Object[] array && array.length == 0) {
            log.warn("Empty array for filter: {} - {}",
                    fgBrightYellow(filter.getField()),
                    fgBrightYellow(filter.getFilterOperator()));
            return;
        }

        Path<?> path = resolvePath(root, filter.getField());
        predicates.add(createPredicate(filter, cb, path));
    }

    private Predicate createPredicate(ValueFilter filter, CriteriaBuilder cb, Path<?> path) {
        return switch (filter.getFilterOperator()) {
            case LIKE -> cb.like(path.as(String.class), "%" + filter.getValue() + "%");
            case EQUALS -> cb.equal(path, filter.getValue());
            case IN -> path.in(convertToCollection(filter.getValue()));
            case IS_NULL -> cb.isNull(path);
            case GREATER_THAN -> compare(cb, path, filter.getValue(), CompareOperator.GT);
            case GREATER_THAN_OR_EQUAL -> compare(cb, path, filter.getValue(), CompareOperator.GE);
            case LESS_THAN -> compare(cb, path, filter.getValue(), CompareOperator.LT);
            case LESS_THAN_OR_EQUAL -> compare(cb, path, filter.getValue(), CompareOperator.LE);
        };
    }

    @SuppressWarnings("unchecked")
    private Predicate compare(CriteriaBuilder cb, Path<?> path, Object value, CompareOperator operator) {
        Comparable<Object> comparableValue = (Comparable<Object>) value;
        return switch (operator) {
            case GT -> cb.greaterThan(path.as(comparableValue.getClass()), comparableValue);
            case GE -> cb.greaterThanOrEqualTo(path.as(comparableValue.getClass()), comparableValue);
            case LT -> cb.lessThan(path.as(comparableValue.getClass()), comparableValue);
            case LE -> cb.lessThanOrEqualTo(path.as(comparableValue.getClass()), comparableValue);
        };
    }

    private List<?> convertToCollection(Object value) {
        if (value instanceof Iterable<?> iterable) {
            return StreamSupport.stream(iterable.spliterator(), false).toList();
        } else if (value instanceof Object[] array) {
            return Arrays.asList(array);
        }
        throw new IllegalArgumentException("Invalid value type for IN operator: " + value.getClass());
    }

    private void applyOrder(OrderedCriteria criteria, CriteriaQuery<E> query) {
        if (criteria.getOrder().getType() == OrderType.NONE) {
            log.debug("Skipping order: {}", fgBrightRed(criteria.getOrder()));
            return;
        }

        // Get the existing root from the query instead of creating a new one
        @SuppressWarnings("unchecked")
        Root<E> root = (Root<E>) query.getRoots().iterator().next();
        Path<?> path = resolvePath(root, criteria.getOrder().getField());
        applyOrderDirection(criteria, query, path);
    }

    private void applyOrderDirection(OrderedCriteria criteria, CriteriaQuery<E> query, Path<?> path) {
        switch (criteria.getOrder().getType()) {
            case ASC -> query.orderBy(entityManager.getCriteriaBuilder().asc(path));
            case DESC -> query.orderBy(entityManager.getCriteriaBuilder().desc(path));
            default -> log.warn("Unsupported order type: {}", criteria.getOrder().getType());
        }
    }

    private CriteriaUpdate<E> buildUpdateQuery(Criteria criteria, FieldUpdate[] fieldUpdates) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<E> update = cb.createCriteriaUpdate(entityClass);
        Root<E> root = update.from(entityClass);

        applyFieldUpdates(update, root, fieldUpdates);
        applyCriteria(criteria, cb, update, root);

        return update;
    }

    private void applyCriteria(Criteria criteria, CriteriaBuilder cb, CriteriaUpdate<E> update, Root<E> root) {
        List<Predicate> predicates = buildPredicates(criteria.getFilters(), cb, root);
        update.where(predicates.toArray(Predicate[]::new));
    }

    private Result<?, F> handleException(Exception e) {
        return exceptionFailureHandler.handle(e);
    }

    private enum CompareOperator {
        GT, GE, LT, LE
    }
}