package com.vet.hc.api.product.application.service;

import com.vet.hc.api.product.application.port.in.DeleteProductPort;
import com.vet.hc.api.product.domain.failure.ProductFailure;
import com.vet.hc.api.product.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to delete a product.
 */
@NoArgsConstructor
public class DeleteProductService implements DeleteProductPort {
    private ProductRepository productRepository;

    @Inject
    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result<Void, ProductFailure> deleteById(Long id) {
        var result = productRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(ProductFailure.NOT_FOUND);
        }

        return Result.success();
    }
}
