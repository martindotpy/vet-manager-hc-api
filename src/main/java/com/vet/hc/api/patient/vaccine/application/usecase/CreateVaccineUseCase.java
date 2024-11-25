package com.vet.hc.api.patient.vaccine.application.usecase;

import com.vet.hc.api.patient.core.domain.model.Patient;
import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;
import com.vet.hc.api.patient.vaccine.application.port.in.CreateVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.patient.vaccine.domain.payload.CreateVaccinePayload;
import com.vet.hc.api.patient.vaccine.domain.repository.VaccineRepository;
import com.vet.hc.api.shared.domain.query.Result;
import com.vet.hc.api.shared.domain.repository.RepositoryFailure;
import com.vet.hc.api.user.core.domain.model.User;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to create a vaccine.
 */
@Slf4j
@NoArgsConstructor
public final class CreateVaccineUseCase implements CreateVaccinePort {
    private VaccineRepository vaccineRepository;

    private final VaccineMapper vaccineMapper = VaccineMapper.INSTANCE;

    @Inject
    public CreateVaccineUseCase(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public Result<VaccineDto, VaccineFailure> create(CreateVaccinePayload payload) {
        Vaccine vaccine = Vaccine.builder()
                .name(payload.getName())
                .dose(payload.getDose())
                .vaccinatedAt(payload.getVaccinatedAt())
                .patient(Patient.builder().id(payload.getPatientId()).build())
                .vaccinator(User.builder().id(payload.getVaccinatorId()).build())
                // .productSale(ProductSale.builder().id(payload.getProductSaleId()).build())
                .build();

        var result = vaccineRepository.save(vaccine);

        if (result.isFailure()) {
            RepositoryFailure failure = result.getFailure();

            return switch (failure) {
                default -> {
                    log.error("Unexpected error creating vaccine with name `{}`", payload.getName());

                    yield Result.failure(VaccineFailure.UNEXPECTED);
                }
            };
        }

        Vaccine createdVaccine = result.getSuccess();

        log.info("Vaccine with name `{}` created", createdVaccine.getName());

        return Result.success(vaccineMapper.toDto(createdVaccine));
    }
}