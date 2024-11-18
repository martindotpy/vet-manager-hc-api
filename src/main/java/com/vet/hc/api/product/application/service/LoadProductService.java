package com.vet.hc.api.product.application.service;

import java.util.Optional;

import com.vet.hc.api.product.adapter.in.response.PaginatedProduct;
import com.vet.hc.api.product.adapter.out.mapper.ProductMapper;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.application.port.in.LoadProductPort;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.product.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to load a product.
 */
@NoArgsConstructor
public class LoadProductService implements LoadProductPort {
    private ProductRepository productRepository;

    @Inject
    public LoadProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Result<ProductDto, ProductFailure> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return Result.failure(ProductFailure.NOT_FOUND);
        }

        Product productFound = product.get();

        return Result.success(productMapper.toDto(productFound));
    }

    @Override
    public Result<PaginatedProduct, ProductFailure> match(Criteria criteria, Iterable<Integer> categoryIds) {
        var response = productRepository.match(criteria, categoryIds);

        if (response.isFailure()) {
            return switch (response.getFailure()) {
                case FIELD_NOT_FOUND -> Result.failure(ProductFailure.FIELD_NOT_FOUND);
                default -> Result.failure(ProductFailure.UNEXPECTED);
            };
        }

        var success = response.getSuccess();

        return Result.success(PaginatedProduct.builder()
                .content(success.getContent().stream()
                        .map(productMapper::toDto)
                        .toList())
                .page(success.getPage())
                .size(success.getSize())
                .totalElements(success.getTotalElements())
                .totalPages(success.getTotalPages())
                .build());
    }
}
