package com.vet.hc.api.bill.core.application.usecase;

import static com.vet.hc.api.bill.core.application.util.PaymentStatusChecker.applyDiscount;
import static com.vet.hc.api.bill.core.application.util.PaymentStatusChecker.hasIncreasedTotalPaid;
import static com.vet.hc.api.bill.core.application.util.PaymentStatusChecker.isPaid;

import java.time.LocalDateTime;
import java.util.Optional;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.bill.core.application.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.UpdateBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.core.domain.payload.UpdateBillPayload;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update an bill .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateBillUseCase implements UpdateBillPort {
    private final GetAuthenticatedUserPort getAuthenticatedUserPort;
    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Override
    public Result<BillDto, BillFailure> update(UpdateBillPayload payload) {
        log.info("Updating bill with id {}", payload.getId());

        Optional<Bill> bill = billRepository.findById(payload.getId());

        if (bill.isEmpty()) {
            log.error("Bill with id {} not found", payload.getId());

            return Result.failure(BillFailure.NOT_FOUND);
        }

        Bill billFound = bill.get();

        if (billFound.isPaid()) {
            log.error("Bill with id {} is already paid", payload.getId());

            return Result.failure(BillFailure.CANNOT_UPDATE_PAID_BILL);
        }

        UserDto user = getAuthenticatedUserPort.get().get();
        Double totalApplied = applyDiscount(payload.getTotal(), payload.getDiscount());
        boolean isPaid = isPaid(totalApplied, payload.getTotalPaid());
        boolean hasIncreasedTotalPaid = hasIncreasedTotalPaid(payload.getTotalPaid(), billFound.getTotalPaid());

        Bill billToUpdate = Bill.builder()
                .id(payload.getId())
                .total(payload.getTotal())
                .discount(payload.getDiscount())
                .totalPaid(payload.getTotalPaid())
                .paid(isPaid)
                .lastPaidDateTime(hasIncreasedTotalPaid ? LocalDateTime.now() : billFound.getLastPaidDateTime())
                .updatedBy(User.builder().id(user.getId()).build())
                .createdBy(billFound.getCreatedBy())
                .client(billFound.getClient())
                .appointmentSales(billFound.getAppointmentSales())
                .productSales(billFound.getProductSales())
                .treatmentSales(billFound.getTreatmentSales())
                .build();

        var result = billRepository.save(billToUpdate);

        if (result.isFailure()) {
            log.error("Error updating bill : {}", result.getFailure());

            return Result.failure(BillFailure.UNEXPECTED);
        }

        Bill billUpdated = result.getSuccess();

        log.info("Bill with id {} updated", billUpdated.getId());

        return Result.success(billMapper.toDto(billUpdated));
    }
}
