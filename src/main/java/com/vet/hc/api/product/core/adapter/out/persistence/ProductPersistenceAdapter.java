package com.vet.hc.api.product.core.adapter.out.persistence;

import java.util.Optional;

import com.vet.hc.api.auth.core.adapter.annotations.PersistenceAdapter;
import com.vet.hc.api.product.core.adapter.out.persistence.repository.ProductHibernateRepository;
import com.vet.hc.api.product.core.application.mapper.ProductMapper;
import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.product.core.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for product persistence.
 */
@Slf4j
@RequiredArgsConstructor
@PersistenceAdapter
public final class ProductPersistenceAdapter implements ProductRepository {
    private final ProductHibernateRepository productHibernateRepository;

    private final ProductMapper productMapper;

    @Override
    public Optional<Product> findById(Long id) {
        return productHibernateRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public Result<Paginated<Product>, ProductFailure> match(Criteria criteria,
            Iterable<Integer> categoryIds) {
        try {
            var response = productHibernateRepository.match(criteria, categoryIds);

            return Result.success(
                    Paginated.<Product>builder()
                            .content(response.getContent().stream()
                                    .map(productMapper::toDomain)
                                    .toList())
                            .page(response.getPage())
                            .size(response.getSize())
                            .totalElements(response.getTotalElements())
                            .totalPages(response.getTotalPages())
                            .build());
        } catch (IllegalArgumentException e) {
            log.warn("Field not found in criteria: {}", e.getMessage());
            return Result.failure(ProductFailure.FIELD_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(ProductFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Product, ProductFailure> save(Product product) {
        try {
            return Result
                    .success(productMapper.toDomain(productHibernateRepository.save(productMapper.toEntity(product))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(ProductFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, ProductFailure> deleteById(Long id) {
        try {
            productHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(ProductFailure.NOT_FOUND);
        }
    }
}
