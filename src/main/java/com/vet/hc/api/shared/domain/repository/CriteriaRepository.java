package com.vet.hc.api.shared.domain.repository;

import java.util.Collection;
import java.util.List;

import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.criteria.Filter;
import com.vet.hc.api.shared.domain.criteria.Order;
import com.vet.hc.api.shared.domain.criteria.OrderedCriteria;
import com.vet.hc.api.shared.domain.criteria.PaginatedCriteria;
import com.vet.hc.api.shared.domain.query.FieldUpdate;
import com.vet.hc.api.shared.domain.query.Paginated;

/**
 * Criteria repository.
 *
 * @param <E>  Domain type.
 * @param <ID> Domain id type.
 * @param <F>  Failure type.
 */
public interface CriteriaRepository<E, ID> extends BasicRepository<E, ID> {
    /**
     * Finds an entity by criteria.
     *
     * <p>
     * The order will be none, even if the criteria has an order.
     * </p>
     *
     * @param criteria The criteria to use.
     * @return The entity found
     */
    E findBy(Criteria criteria);

    /**
     * Finds an entity by criteria.
     *
     * @param necessaryFilter The necessary filter to use.
     * @param filters         The filters to use.
     * @return The entity found
     */
    default E findBy(Filter necessaryFilter, Filter... filters) {
        return findBy(Criteria.of(necessaryFilter, filters));
    }

    /**
     * Finds an entity by criteria.
     *
     * @param filters The filters to use.
     * @return The entity found
     */
    default E findBy(Collection<Filter> filters) {
        return findBy(Criteria.of(filters));
    }

    /**
     * Finds all entities by criteria.
     *
     * @param criteria The criteria to use.
     * @return The list of entities found
     */
    List<E> findAllBy(OrderedCriteria criteria);

    /**
     * Finds all entities by criteria.
     *
     * @param necessaryFilter The necessary filter to use.
     * @param filters         The filters to use.
     * @return The list of entities found
     */
    default List<E> findAllBy(Filter necessaryFilter, Filter... filters) {
        return findAllBy(OrderedCriteria.of(Order.none(), necessaryFilter, filters));
    }

    /**
     * Finds all entities by criteria.
     *
     * @param filters The filters to use.
     * @return The list of entities found
     */
    default List<E> findAllBy(Collection<Filter> filters) {
        return findAllBy(OrderedCriteria.of(Order.none(), filters));
    }

    /**
     * Finds all entities by criteria.
     *
     * @param order           The order to use.
     * @param necessaryFilter The necessary filter to use.
     * @param filters         The filters to use.
     * @return The list of entities found
     */
    default List<E> findAllBy(Order order, Filter necessaryFilter, Filter... filters) {
        return findAllBy(OrderedCriteria.of(order, necessaryFilter, filters));
    }

    /**
     * Finds all entities by criteria.
     *
     * @param order   The order to use.
     * @param filters The filters to use.
     * @return The list of entities found
     */
    default List<E> findAllBy(Order order, Collection<Filter> filters) {
        return findAllBy(OrderedCriteria.of(order, filters));
    }

    /**
     * Finds all entities paginated by criteria.
     *
     * @param criteria The paginated criteria to use.
     * @return The paginated list of entities found
     */
    Paginated<E> findPaginatedBy(PaginatedCriteria criteria);

    /**
     * Counts the number of entities by criteria.
     *
     * @param criteria The criteria to use.
     * @return The number of entities found
     */
    Long countBy(Criteria criteria);

    /**
     * Counts the number of entities by criteria.
     *
     * @param criteria             The criteria to use.
     * @param necessaryFieldUpdate The necessary filter update to use.
     * @param fieldUpdates         The filter updates to use.
     * @return The number of entities found
     */
    Integer updateBy(Criteria criteria, FieldUpdate necessaryFieldUpdate, FieldUpdate... fieldUpdates);
}
