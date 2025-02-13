package com.vluepixel.vetmanager.api.shared.domain.criteria;

import lombok.Getter;
import lombok.ToString;

/**
 * Filter class.
 */
@Getter
@ToString
public abstract sealed class Filter permits ValueFilter, LogicalFilter {
    /**
     * Filter operator.
     *
     * @param field Field.
     * @param value Value.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static Filter like(String field, Object value) {
        return new ValueFilter(field, FilterOperator.LIKE, value);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @param value Value.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static Filter equal(String field, Object value) {
        return new ValueFilter(field, FilterOperator.EQUALS, value);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @param value Value.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static <T extends Iterable<?>> Filter in(String field, T value) {
        return new ValueFilter(field, FilterOperator.IN, value);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static Filter in(String field, Object necessaryValue, Object... values) {
        Object[] allValues = new Object[values.length + 1];
        allValues[0] = necessaryValue;
        System.arraycopy(values, 0, allValues, 1, values.length);

        return new ValueFilter(field, FilterOperator.IN, values);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @return Filter.
     * @throws NullPointerException If the field is null.
     */
    public static Filter isNull(String field) {
        return new ValueFilter(field, FilterOperator.IS_NULL);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static <T extends Comparable<?>> Filter greaterThan(String field, T value) {
        return new ValueFilter(field, FilterOperator.GREATER_THAN, value);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static <T extends Comparable<?>> Filter greaterThanOrEqual(String field, T value) {
        return new ValueFilter(field, FilterOperator.GREATER_THAN_OR_EQUAL, value);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static <T extends Comparable<?>> Filter lessThan(String field, T value) {
        return new ValueFilter(field, FilterOperator.LESS_THAN, value);
    }

    /**
     * Filter operator.
     *
     * @param field Field.
     * @return Filter.
     * @throws NullPointerException If the field or values is null.
     */
    public static <T extends Comparable<?>> Filter lessThanOrEqual(String field, T value) {
        return new ValueFilter(field, FilterOperator.LESS_THAN_OR_EQUAL, value);
    }

    /**
     * Filter operator.
     *
     * @param necessaryFilter Necessary filter.
     * @param filters         Filters.
     * @return Filter.
     * @throws NullPointerException If the necessary filter or filters is null.
     */
    public static Filter and(Filter necessaryFilter, Filter... filters) {
        return new LogicalFilter(LogicalOperator.AND, necessaryFilter, filters);
    }

    /**
     * Filter operator.
     *
     * @param necessaryFilter Necessary filter.
     * @param filters         Filters.
     * @return Filter.
     * @throws NullPointerException If the necessary filter or filters is null.
     */
    public static Filter or(Filter necessaryFilter, Filter... filters) {
        return new LogicalFilter(LogicalOperator.OR, necessaryFilter, filters);
    }

    /**
     * Filter operator.
     *
     * @param filter Filter.
     * @return Filter.
     * @throws NullPointerException If the filter is null.
     */
    public static Filter not(Filter filter) {
        return new LogicalFilter(LogicalOperator.NOT, filter);
    }
}
