package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.adapter.out.mapper.ProductMapper;
import com.vet.hc.api.product.application.dto.ProductDto;
import com.vet.hc.api.product.application.port.in.CreateProductPort;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.product.domain.model.Category;
import com.vet.hc.api.product.domain.model.Product;
import com.vet.hc.api.product.domain.payload.CreateProductPayload;
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
            return Result.failure(ProductFailure.DUPLICATE);
        }

        return Result.success(productMapper.toDto(result.getSuccess()));
    }
}
