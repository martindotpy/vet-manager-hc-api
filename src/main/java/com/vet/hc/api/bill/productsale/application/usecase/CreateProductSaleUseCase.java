package com.vet.hc.api.bill.productsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.productsale.adapter.out.mapper.ProductSaleMapper;
import com.vet.hc.api.bill.productsale.application.port.in.CreateProductSalePort;
import com.vet.hc.api.bill.productsale.domain.dto.ProductSaleDto;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.model.ProductSale;
import com.vet.hc.api.bill.productsale.domain.payload.CreateProductSalePayload;
import com.vet.hc.api.bill.productsale.domain.repository.ProductSaleRepository;
import com.vet.hc.api.product.core.domain.model.Product;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a product sale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateProductSaleUseCase implements CreateProductSalePort {
    private final ProductSaleRepository productSaleRepository;
    private final ProductSaleMapper productSaleMapper;

    @Override
    public Result<ProductSaleDto, ProductSaleFailure> create(CreateProductSalePayload payload) {
        ProductSale productSale = ProductSale.builder()
                .price(payload.getPrice())
                .discount(payload.getDiscount())
                .quantity(payload.getQuantity())
                .product(Product.builder().id(payload.getProductId()).build())
                .seller(User.builder().id(payload.getSellerId()).build())
                .bill(Bill.builder().id(payload.getBillId()).build())
                .build();

        var result = productSaleRepository.save(productSale);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Unexpected error creating product sale with bill id `{}`", payload.getBillId());

                    yield Result.failure(ProductSaleFailure.UNEXPECTED);
                }
            };
        }

        ProductSale createdProductSale = result.getSuccess();

        log.info("Product sale with bill id `{}` created", payload.getBillId());

        return Result.success(productSaleMapper.toDto(createdProductSale));
    }
}
