package com.vet.hc.api.product.core.application.usecase;

import com.vet.hc.api.product.category.domain.model.Category;
import com.vet.hc.api.product.category.domain.payload.UpdateProductPayload;
import com.vet.hc.api.product.core.application.mapper.ProductMapper;
import com.vet.hc.api.product.core.application.port.in.UpdateProductPort;
import com.vet.hc.api.product.core.domain.dto.ProductDto;
import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.product.core.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Use case for updating a product.
 */
@NoArgsConstructor
public class UpdateProductUseCase implements UpdateProductPort {
    private ProductRepository productRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Inject
    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductDto, ProductFailure> update(UpdateProductPayload payload) {
        Product product = Product.builder()
                .id(payload.getId())
                .name(payload.getName())
                .description(payload.getDescription())
                .quantity(payload.getQuantity())
                .price(payload.getPrice())
                .categories(payload.getCategoryIds().stream().map(id -> Category.builder().id(id).build()).toList())
                .build();

        var result = productRepository.save(product);

        if (result.isFailure()) {
            return switch (result.getFailure()) {
                case NOT_FOUND -> Result.failure(ProductFailure.NOT_FOUND);
                default -> Result.failure(ProductFailure.UNEXPECTED);
            };
        }

        return Result.success(productMapper.toDto(result.getSuccess()));
    }
}
