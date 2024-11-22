package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.bill.core.adapter.out.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.CreateBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.core.domain.payload.CreateBillPayload;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create an appointment type.
 */
@Slf4j
@NoArgsConstructor
public final class CreateBillUseCase implements CreateBillPort {
    private BillRepository billRepository;

    private final BillMapper billMapper = BillMapper.INSTANCE;

    @Inject
    public CreateBillUseCase(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Result<BillDto, BillFailure> create(CreateBillPayload payload) {
        log.info("Creating appointment type: {}", payload.getName());

        Bill bill = Bill.builder()
                .name(payload.getName())
                .durationInMinutes(payload.getDurationInMinutes())
                .price(payload.getPrice())
                .build();

        var result = billRepository.save(bill);

        if (result.isFailure()) {
            log.error("Failed to create appointment type: {}", result.getFailure().getMessage());

            RepositoryFailure failure = result.getFailure();

            if (failure == RepositoryFailure.DUPLICATED) {
                if (failure.getField().equals("name"))
                    return Result.failure(BillFailure.DUPLICATED_NAME);
                else
                    return Result.failure(BillFailure.UNEXPECTED);
            }

            return Result.failure(BillFailure.UNEXPECTED);
        }

        Bill createdBill = result.getSuccess();

        log.info("Appointment type created: {}", createdBill.getName());

        return Result.success(billMapper.toDto(createdBill));
    }
}
