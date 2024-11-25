package com.vet.hc.api.bill.productsale.application.usecase;

import java.util.Optional;

import com.vet.hc.api.bill.productsale.adapter.out.mapper.ProductSaleMapper;
import com.vet.hc.api.bill.productsale.application.port.in.UpdateProductSalePort;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.productsale.domain.payload.UpdateProductSalePayload;
import com.vet.hc.api.bill.productsale.domain.repository.ProductSaleRepository;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a productSale.
 */
@Slf4j
@NoArgsConstructor
public final class UpdateProductSaleUseCase implements UpdateProductSalePort {
    private ProductSaleRepository productSaleRepository;

    private final ProductSaleMapper productSaleMapper = ProductSaleMapper.INSTANCE;

    @Inject
    public UpdateProductSaleUseCase(ProductSaleRepository productSaleRepository) {
        this.productSaleRepository = productSaleRepository;
    }

    @Override
    public Result<ProductSaleDto, ProductSaleFailure> update(UpdateProductSalePayload payload) {
        log.info("Updating product sale with id `{}`", payload.getId());

        Optional<ProductSale> productSale = productSaleRepository.findById(payload.getId());

        if (productSale.isEmpty()) {
            log.error("Product sale with id `{}` not found", payload.getId());

            return Result.failure(ProductSaleFailure.NOT_FOUND);
        }

        ProductSale productSaleFound = productSale.get();
        ProductSale productSaleToUpdate = ProductSale.builder()
                .id(payload.getId())
                .price(payload.getPrice())
                .discount(payload.getDiscount())
                .quantity(payload.getQuantity())
                .product(Product.builder().id(payload.getProductId()).build())
                .seller(productSaleFound.getSeller())
                .bill(productSaleFound.getBill())
                .build();

        var result = productSaleRepository.save(productSaleToUpdate);

        if (result.isFailure()) {
            log.error("Error updating product sale: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (repositoryFailure) {
                default -> Result.failure(ProductSaleFailure.UNEXPECTED);
            };
        }

        ProductSale productSaleUpdated = result.getSuccess();

        log.info("Product sale with id `{}` updated", productSaleUpdated.getId());

        return Result.success(productSaleMapper.toDto(productSaleUpdated));
    }
}
