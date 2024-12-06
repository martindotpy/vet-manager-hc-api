package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.appointmentsale.application.port.in.CreateAppointmentSalePort;
import com.vet.hc.api.bill.appointmentsale.domain.failure.AppointmentSaleFailure;
import com.vet.hc.api.bill.appointmentsale.domain.payload.CreateAppointmentSalePayload;
import com.vet.hc.api.bill.core.application.port.in.AddAppointmentSaleToBillPort;
import com.vet.hc.api.bill.core.application.port.in.FindBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public final class AddAppointmentSaleToBillUseCase implements AddAppointmentSaleToBillPort {
    private final CreateAppointmentSalePort createAppointmentSalePort;
    private final FindBillPort findBillPort;

    @Override
    public Result<BillDto, BillFailure> add(CreateAppointmentSalePayload payload) {
        log.info("Adding appointment to bill with id `{}`", payload.getBillId());

        var result = createAppointmentSalePort.create(payload);

        if (result.isFailure()) {
            AppointmentSaleFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to add appointment sale to bill with id `{}`",
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
