package com.vet.hc.api.bill.productsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.productsale.adapter.out.mapper.ProductSaleMapper;
import com.vet.hc.api.bill.productsale.application.port.in.FindProductSalePort;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.productsale.domain.repository.ProductSaleRepository;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a productSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class FindProductSaleUseCase implements FindProductSalePort {
    private final ProductSaleRepository productSaleRepository;
    private final ProductSaleMapper productSaleMapper;

    @Override
    public Result<ProductSaleDto, ProductSaleFailure> findById(Long id) {
        log.info("Finding product sale by id `{}`", id);

        var result = productSaleRepository.findById(id);

        if (result.isEmpty()) {
            log.error("ProductSale not found with id `{}`", id);

            return Result.failure(ProductSaleFailure.NOT_FOUND);
        }

        ProductSale productSale = result.get();

        log.info("Product sale with id `{}` found`", productSale.getId());

        return Result.success(productSaleMapper.toDto(productSale));
    }
}
