package com.vet.hc.api.product.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.product.core.application.port.in.DeleteProductPort;
import com.vet.hc.api.product.core.domain.failure.ProductFailure;
import com.vet.hc.api.product.core.domain.repository.ProductRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;

/**
 * Use case to delete a product.
 */
@UseCase
@RequiredArgsConstructor
public final class DeleteProductUseCase implements DeleteProductPort {
    private final ProductRepository productRepository;

    @Override
    public Result<Void, ProductFailure> deleteById(Long id) {
        var result = productRepository.deleteById(id);

        if (result.isFailure()) {
            return Result.failure(ProductFailure.NOT_FOUND);
        }

        return Result.success();
    }
}
