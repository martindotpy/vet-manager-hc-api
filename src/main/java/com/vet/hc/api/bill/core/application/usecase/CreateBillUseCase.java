package com.vet.hc.api.bill.core.application.usecase;

import static com.vet.hc.api.bill.core.application.util.PaymentStatusChecker.applyDiscount;
import static com.vet.hc.api.bill.core.application.util.PaymentStatusChecker.isPaid;

import java.time.LocalDateTime;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.auth.core.application.port.in.GetAuthenticatedUserPort;
import com.vet.hc.api.bill.core.application.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.CreateBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.core.domain.payload.CreateBillPayload;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.client.core.domain.model.Client;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.dto.UserDto;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an bill .
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateBillUseCase implements CreateBillPort {
    private final GetAuthenticatedUserPort getAuthenticatedUserPort;
    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Override
    public Result<BillDto, BillFailure> create(CreateBillPayload payload) {
        log.info("Creating bill with client id `{}`", payload.getClientId());

        UserDto user = getAuthenticatedUserPort.get().get();
        Double totalApplied = applyDiscount(payload.getTotal(), payload.getDiscount());
        boolean isPaid = isPaid(totalApplied, payload.getTotalPaid());

        Bill bill = Bill.builder()
                .total(payload.getTotal())
                .discount(payload.getDiscount())
                .totalPaid(payload.getTotalPaid())
                .paid(isPaid)
                .lastPaidDateTime(LocalDateTime.now())
                .createdBy(User.builder().id(user.getId()).build())
                .updatedBy(User.builder().id(user.getId()).build())
                .client(Client.builder().id(payload.getClientId()).build())
                .build();

        var result = billRepository.save(bill);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Failed to create bill with client id `{}`", payload.getClientId());
                    yield Result.failure(BillFailure.UNEXPECTED);
                }
            };
        }

        Bill createdBill = result.getSuccess();

        log.info("Bill created: {}", createdBill.getId());

        return Result.success(billMapper.toDto(createdBill));
    }
}
