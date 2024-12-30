package com.vet.hc.api.bill.treatmentsale.application.usecase;

import java.util.Optional;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.treatmentsale.adapter.out.mapper.TreatmentSaleMapper;
import com.vet.hc.api.bill.treatmentsale.application.port.in.UpdateTreatmentSalePort;
import com.vet.hc.api.bill.treatmentsale.domain.dto.TreatmentSaleDto;
import com.vet.hc.api.bill.treatmentsale.domain.failure.TreatmentSaleFailure;
import com.vet.hc.api.bill.treatmentsale.domain.model.TreatmentSale;
import com.vet.hc.api.bill.treatmentsale.domain.payload.UpdateTreatmentSalePayload;
import com.vet.hc.api.bill.treatmentsale.domain.repository.TreatmentSaleRepository;
import com.vet.hc.api.medicalrecord.treatment.domain.model.Treatment;
import com.vet.hc.api.shared.domain.query.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a treatmentSale.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateTreatmentSaleUseCase implements UpdateTreatmentSalePort {
    private final TreatmentSaleRepository treatmentSaleRepository;
    private final TreatmentSaleMapper treatmentSaleMapper;

    @Override
    public Result<TreatmentSaleDto, TreatmentSaleFailure> update(UpdateTreatmentSalePayload payload) {
        log.info("Updating treatment sale with id `{}`", payload.getId());

        Optional<TreatmentSale> treatmentSale = treatmentSaleRepository.findById(payload.getId());

        if (treatmentSale.isEmpty()) {
            log.error("Treatment sale with id `{}` not found", payload.getId());

            return Result.failure(TreatmentSaleFailure.NOT_FOUND);
        }

        TreatmentSale treatmentSaleFound = treatmentSale.get();
        TreatmentSale treatmentSaleToUpdate = TreatmentSale.builder()
                .id(payload.getId())
                .price(payload.getPrice())
                .discount(payload.getDiscount())
                .treatment(Treatment.builder().id(payload.getTreatmentId()).build())
                .seller(treatmentSaleFound.getSeller())
                .bill(treatmentSaleFound.getBill())
                .build();

        var result = treatmentSaleRepository.save(treatmentSaleToUpdate);

        if (result.isFailure()) {
            log.error("Error updating treatment sale: {}", result.getFailure());

            return Result.failure(TreatmentSaleFailure.UNEXPECTED);
        }

        TreatmentSale treatmentSaleUpdated = result.getSuccess();

        log.info("Treatment sale with id `{}` updated", treatmentSaleUpdated.getId());

        return Result.success(treatmentSaleMapper.toDto(treatmentSaleUpdated));
    }
}
