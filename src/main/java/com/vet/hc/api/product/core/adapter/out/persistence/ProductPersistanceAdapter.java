package com.vet.hc.api.product.core.adapter.out.persistence;

import java.util.Optional;

import com.vet.hc.api.product.core.adapter.out.persistence.repository.ProductHibernateRepository;
import com.vet.hc.api.product.core.application.mapper.ProductMapper;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.product.core.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Paginated;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Adapter for product persistance.
 */
@Slf4j
@NoArgsConstructor
public class ProductPersistanceAdapter implements ProductRepository {
    private ProductHibernateRepository productHibernateRepository;

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Inject
    public ProductPersistanceAdapter(ProductHibernateRepository productHibernateRepository) {
        this.productHibernateRepository = productHibernateRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productHibernateRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public Result<Paginated<Product>, RepositoryFailure> match(Criteria criteria,
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
            return Result.failure(RepositoryFailure.FIELD_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Product, RepositoryFailure> save(Product product) {
        try {
            return Result
                    .success(productMapper.toDomain(productHibernateRepository.save(productMapper.toEntity(product))));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(RepositoryFailure.UNEXPECTED);
        }
    }

    @Override
    public Result<Void, RepositoryFailure> deleteById(Long id) {
        try {
            productHibernateRepository.deleteById(id);

            return Result.success();
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            return Result.failure(RepositoryFailure.NOT_FOUND);
        }
    }
}
