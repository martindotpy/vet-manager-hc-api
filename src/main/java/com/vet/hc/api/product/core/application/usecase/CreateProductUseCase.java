package com.vet.hc.api.product.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.payload.CreateProductPayload;
import com.vet.hc.api.product.core.application.mapper.ProductMapper;
import com.vet.hc.api.product.core.application.port.in.CreateProductPort;
import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.product.core.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Use case for creating a new product.
 */
@UseCase
@RequiredArgsConstructor
public final class CreateProductUseCase implements CreateProductPort {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Result<ProductDto, ProductFailure> create(CreateProductPayload payload) {
        Product product = Product.builder()
                .name(payload.getName())
                .price(payload.getPrice())
                .description(payload.getDescription())
                .quantity(payload.getQuantity())
                .categories(payload.getCategoryIds().stream().map(id -> Category.builder().id(id).build()).toList())
                .build();

        var result = productRepository.save(product);

        if (result.isFailure()) {
            return Result.failure(ProductFailure.DUPLICATED_NAME);
        }

        return Result.success(productMapper.toDto(result.getSuccess()));
    }
}
