package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.application.port.in.AddProductSaleToBillPort;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.productsale.application.port.in.CreateProductSalePort;
import com.vet.hc.api.bill.productsale.domain.failure.ProductSaleFailure;
import com.vet.hc.api.bill.productsale.domain.payload.CreateProductSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public final class AddProductSaleToBillUseCase implements AddProductSaleToBillPort {
    private final CreateProductSalePort createProductSalePort;
    private final FindBillPort findBillPort;

    @Override
    public Result<BillDto, BillFailure> add(CreateProductSalePayload payload) {
        log.info("Adding product to bill with id `{}`", payload.getBillId());

        var result = createProductSalePort.create(payload);

        if (result.isFailure()) {
            ProductSaleFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add product sale to bill with id `{}`",
                            payload.getBillId());

                    yield Result.failure(BillFailure.UNEXPECTED);
                }
            };
        }
        var resultFindBill = findBillPort.findById(payload.getBillId());

        if (resultFindBill.isFailure())
            return Result.failure(resultFindBill.getFailure());

        return Result.success(resultFindBill.getSuccess());
    }
}
