package com.vet.hc.api.bill.core.application.usecase;

import com.vet.hc.api.bill.core.adapter.out.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.UpdateBillPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.failure.BillFailure;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.core.domain.payload.UpdateBillPayload;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update an appointment type.
 */
@Slf4j
@NoArgsConstructor
public final class UpdateBillUseCase implements UpdateBillPort {
    private BillRepository billRepository;

    private final BillMapper billMapper = BillMapper.INSTANCE;

    @Inject
    public UpdateBillUseCase(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Result<BillDto, BillFailure> update(UpdateBillPayload payload) {
        log.info("Updating appointment type with id {}", payload.getId());

        Bill billToUpdate = Bill.builder()
                .id(payload.getId())
                .name(payload.getName())
                .durationInMinutes(payload.getDurationInMinutes())
                .price(payload.getPrice())
                .build();

        var result = billRepository.save(billToUpdate);

        if (result.isFailure()) {
            log.error("Error updating appointment type: {}", result.getFailure());

            RepositoryFailure repositoryFailure = result.getFailure();

            return switch (result.getFailure()) {
                case DUPLICATED -> {
                    if (repositoryFailure.getField().equals("name"))
                        yield Result.failure(BillFailure.DUPLICATED_NAME);

                    yield Result.failure(BillFailure.UNEXPECTED);
                }
                default -> Result.failure(BillFailure.UNEXPECTED);
            };
        }

        Bill bill = result.getSuccess();

        log.info("Appointment type with id {} updated", bill.getId());

        return Result.success(billMapper.toDto(bill));
    }
}
