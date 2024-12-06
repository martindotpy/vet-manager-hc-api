package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.application.port.in.AddTreatmentSaleToBillPort;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.treatmentsale.application.port.in.CreateTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.payload.CreateTreatmentSalePayload;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public final class AddTreatmentSaleToBillUseCase implements AddTreatmentSaleToBillPort {
    private final CreateTreatmentSalePort createTreatmentSalePort;
    private final FindBillPort findBillPort;

    @Override
    public Result<BillDto, BillFailure> add(CreateTreatmentSalePayload payload) {
        log.info("Adding treatment to bill with id `{}`", payload.getBillId());

        var result = createTreatmentSalePort.create(payload);

        if (result.isFailure()) {
            TreatmentSaleFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add treatment sale to bill with id `{}`",
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
