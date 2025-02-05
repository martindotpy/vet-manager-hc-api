package com.vet.hc.api.product.core.application.usecase;

import java.util.Optional;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.core.application.mapper.ProductMapper;
import com.vet.hc.api.product.core.application.port.in.FindProductPort;
import com.vet.hc.api.product.core.application.response.PaginatedProduct;
import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.product.core.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.criteria.Criteria;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Use case to load a product.
 */
@UseCase
@RequiredArgsConstructor
public final class FindProductUseCase implements FindProductPort {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

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
