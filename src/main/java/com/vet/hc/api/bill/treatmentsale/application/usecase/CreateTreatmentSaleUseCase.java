package com.vet.hc.api.bill.treatmentsale.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.domain.model.Bill;
import com.vet.hc.api.bill.treatmentsale.adapter.out.mapper.TreatmentSaleMapper;
import com.vet.hc.api.bill.treatmentsale.application.port.in.CreateTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.bill.treatmentsale.domain.payload.CreateTreatmentSalePayload;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a treatment sale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class CreateTreatmentSaleUseCase implements CreateTreatmentSalePort {
    private final TreatmentSaleRepository treatmentSaleRepository;
    private final TreatmentSaleMapper treatmentSaleMapper;

    @Override
    public Result<TreatmentSaleDto, TreatmentSaleFailure> create(CreateTreatmentSalePayload payload) {
        TreatmentSale treatmentSale = TreatmentSale.builder()
                .price(payload.getPrice())
                .discount(payload.getDiscount())
                .treatment(Treatment.builder().id(payload.getTreatmentId()).build())
                .seller(User.builder().id(payload.getSellerId()).build())
                .bill(Bill.builder().id(payload.getBillId()).build())
                .build();

        var result = treatmentSaleRepository.save(treatmentSale);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Unexpected error creating treatment sale with bill id `{}`", payload.getBillId());

                    yield Result.failure(TreatmentSaleFailure.UNEXPECTED);
                }
            };
        }

        TreatmentSale createdTreatmentSale = result.getSuccess();

        log.info("Treatment sale with bill id `{}` created", payload.getBillId());

        return Result.success(treatmentSaleMapper.toDto(createdTreatmentSale));
    }
}
