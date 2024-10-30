package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.adapter.out.mapper.ProductMapper;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.application.port.in.UpdateProductPort;
import com.vet.hc.api.product.domain.command.UpdateProductCommand;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.product.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for updating a product.
 */
@NoArgsConstructor
public class UpdateProductService implements UpdateProductPort {
    private ProductRepository productRepository;

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Inject
    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductDto, ProductFailure> update(UpdateProductCommand command) {
        Product product = Product.builder()
                .id(command.getId())
                .name(command.getName())
                .description(command.getDescription())
                .quantity(command.getQuantity())
                .price(command.getPrice())
                .categories(command.getCategories())
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
