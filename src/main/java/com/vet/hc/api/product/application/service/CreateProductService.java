package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.adapter.out.mapper.ProductMapper;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.application.port.in.CreateProductPort;
import com.vet.hc.api.product.domain.command.CreateProductCommand;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.product.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service for creating a new product.
 */
@NoArgsConstructor
public class CreateProductService implements CreateProductPort {
    private ProductRepository productRepository;

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Inject
    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<ProductDto, ProductFailure> create(CreateProductCommand command) {
        Product product = Product.builder()
                .name(command.getName())
                .price(command.getPrice())
                .description(command.getDescription())
                .quantity(command.getQuantity())
                .categories(command.getCategories())
                .build();

        var result = productRepository.save(product);

        if (result.isFailure()) {
            return Result.failure(ProductFailure.DUPLICATE);
        }

        return Result.success(productMapper.toDto(result.getSuccess()));
    }
}
