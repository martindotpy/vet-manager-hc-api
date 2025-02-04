package com.vet.hc.api.shared.application.adapter.in.util;

import static com.vet.hc.api.shared.domain.result.Result.failure;
import static com.vet.hc.api.shared.domain.result.Result.ok;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.vet.hc.api.shared.domain.failure.Failure;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.result.Result;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Result shortcuts.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResultShortcuts {
    /**
     * Do and map.
     *
     * @param <I>           The input type.
     * @param <R>           The result type.
     * @param <F>           The failure type.
     * @param <NF>          The new failure type.
     * @param supplier      The supplier.
     * @param mapper        The mapper.
     * @param failureMapper The failure mapper.
     * @return The result
     */
    public static <I, R, F extends Failure, NF extends Failure> Result<R, NF> doAndMap(
            Supplier<Result<? extends I, F>> supplier,
            Function<I, R> mapper,
            Function<F, NF> failureMapper) {

        var result = supplier.get();

        if (result.isFailure()) {
            return failure(failureMapper.apply(result.getFailure()));
        }

        return ok(mapper.apply(result.getOk()));
    }

    /**
     * Do and map.
     *
     * @param <I>      The input type.
     * @param <R>      The result type.
     * @param <F>      The failure type.
     * @param supplier The supplier.
     * @param mapper   The mapper.
     * @return The result
     */
    public static <I, R, F extends Failure> Result<R, F> doAndMap(
            Supplier<Result<? extends I, F>> supplier,
            Function<I, R> mapper) {
        return doAndMap(supplier, mapper, Function.identity());
    }

    /**
     * Do and map collection.
     *
     * @param <I>              The input type.
     * @param <C>              The collection type.
     * @param <R>              The result type.
     * @param <F>              The failure type.
     * @param <NF>             The new failure type.
     * @param supplier         The supplier.
     * @param mapper           The mapper.
     * @param collectionMapper The collection mapper.
     * @param failureMapper    The failure mapper.
     * @return The result
     */
    public static <I, C extends Collection<R>, R, F extends Failure, NF extends Failure> Result<C, NF> doAndMapCollection(
            Supplier<Result<? extends Collection<? extends I>, F>> supplier,
            Function<I, R> mapper,
            Collector<R, ?, C> collectionMapper,
            Function<F, NF> failureMapper) {
        var result = supplier.get();

        if (result.isFailure()) {
            return failure(failureMapper.apply(result.getFailure()));
        }

        return ok(result.getOk().stream().map(mapper::apply).collect(collectionMapper));
    }

    /**
     * Do and map collection.
     *
     * @param <I>              The input type.
     * @param <C>              The collection type.
     * @param <R>              The result type.
     * @param <F>              The failure type.
     * @param supplier         The supplier.
     * @param mapper           The mapper.
     * @param collectionMapper The collection mapper.
     * @return The result
     */
    public static <I, C extends Collection<R>, R, F extends Failure> Result<C, F> doAndMapCollection(
            Supplier<Result<? extends Collection<? extends I>, F>> supplier,
            Function<I, R> mapper,
            Collector<R, ?, C> collectionMapper) {
        return doAndMapCollection(supplier, mapper, collectionMapper, Function.identity());
    }

    /**
     * Do and map paginated.
     *
     * @param <I>           The input type.
     * @param <R>           The result type.
     * @param <F>           The failure type.
     * @param <NF>          The new failure type.
     * @param supplier      The supplier.
     * @param mapper        The mapper.
     * @param failureMapper The failure mapper.
     * @return The result
     */
    public static <I, R, F extends Failure, NF extends Failure> Result<Paginated<R>, NF> doAndMapPaginated(
            Supplier<Result<Paginated<? extends I>, F>> supplier,
            Function<I, R> mapper,
            Function<F, NF> failureMapper) {
        var result = supplier.get();

        if (result.isFailure()) {
            return failure(failureMapper.apply(result.getFailure()));
        }

        return ok(result.getOk().map(mapper::apply));
    }

    /**
     * Do and map paginated.
     *
     * @param <I>      The input type.
     * @param <R>      The result type.
     * @param <F>      The failure type.
     * @param supplier The supplier.
     * @param mapper   The mapper.
     * @return The result
     */
    public static <I, R, F extends Failure> Result<Paginated<R>, F> doAndMapPaginated(
            Supplier<Result<Paginated<? extends I>, F>> supplier,
            Function<I, R> mapper) {
        return doAndMapPaginated(supplier, mapper, Function.identity());
    }

    /**
     * Do with empty result.
     *
     * @param <I>           The input type.
     * @param <F>           The failure type.
     * @param <NF>          The new failure type.
     * @param supplier      The supplier.
     * @param failureMapper The failure mapper.
     * @return The result
     */
    public static <I, F extends Failure, NF extends Failure> Result<Void, NF> doWithEmptyResult(
            Supplier<Result<?, F>> supplier,
            Function<F, NF> failureMapper) {
        var result = supplier.get();

        if (result.isFailure()) {
            return failure(failureMapper.apply(result.getFailure()));
        }

        return ok();
    }

    /**
     * Do with empty result.
     *
     * @param <F>      The failure type.
     * @param supplier The supplier.
     * @return The result
     */
    public static <I, F extends Failure> Result<Void, F> doWithEmptyResult(Supplier<Result<?, F>> supplier) {
        return doWithEmptyResult(supplier, Function.identity());
    }
}
