package com.vet.hc.api.bill.productsale.application.usecase;

import com.vet.hc.api.bill.productsale.application.port.in.DeleteProductSalePort;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.repository.ProductSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to delete a productSale.
 */
@Slf4j
@NoArgsConstructor
public final class DeleteProductSaleUseCase implements DeleteProductSalePort {
    private ProductSaleRepository productSaleRepository;

    @Inject
    public DeleteProductSaleUseCase(ProductSaleRepository productSaleRepository) {
        this.productSaleRepository = productSaleRepository;
    }

    @Override
    public Result<Void, ProductSaleFailure> deleteById(Long id) {
        log.info("Deleting productSale with id: {}", id);

        var result = productSaleRepository.deleteById(id);

        if (result.isFailure()) {
            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                case NOT_FOUND -> {
                    log.error("Product sale with id {} not found", id);

                    yield Result.failure(ProductSaleFailure.NOT_FOUND);
                }
                default -> {
                    log.error("Unexpected error deleting product sale with id {}", id);

                    yield Result.failure(ProductSaleFailure.UNEXPECTED);
                }
            };
        }

        log.info("Product sale with id {} deleted", id);

        return Result.success();
    }
}
