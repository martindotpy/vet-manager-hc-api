package com.vet.hc.api.patient.vaccine.application.usecase;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;
import com.vet.hc.api.patient.vaccine.application.port.in.UpdateVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.patient.vaccine.domain.payload.UpdateVaccinePayload;
import com.vet.hc.api.patient.vaccine.domain.repository.VaccineRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.user.core.domain.model.UserImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to update a vaccine.
 */
@Slf4j
@UseCase
@RequiredArgsConstructor
public final class UpdateVaccineUseCase implements UpdateVaccinePort {
    private final VaccineRepository vaccineRepository;
    private final VaccineMapper vaccineMapper;

    @Override
    public Result<VaccineDto, VaccineFailure> update(UpdateVaccinePayload payload) {
        log.info("Updating vaccine with id `{}`", payload.getId());

        Vaccine vaccineToUpdate = Vaccine.builder()
                .id(payload.getId())
                .name(payload.getName())
                .dose(payload.getDose())
                .vaccinatedAt(payload.getVaccinatedAt())
                .patient(Patient.builder().id(payload.getPatientId()).build())
                .vaccinator(UserImpl.builder().id(payload.getVaccinatorId()).build())
                // .productSale(ProductSale.builder().id(payload.getProductSaleId()).build())
                .build();

        var result = vaccineRepository.save(vaccineToUpdate);

        if (result.isFailure()) {
            log.error("Error updating vaccine: {}", result.getFailure());

            return Result.failure(VaccineFailure.UNEXPECTED);
        }

        Vaccine vaccineUpdated = result.getSuccess();

        log.info("Vaccine with id `{}` updated", vaccineUpdated.getId());

        return Result.success(vaccineMapper.toDto(vaccineUpdated));
    }
}
