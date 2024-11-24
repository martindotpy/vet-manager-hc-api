package com.vet.hc.api.patient.vaccine.application.usecase;

import com.vet.hc.api.patient.vaccine.adapter.out.mapper.VaccineMapper;
import com.vet.hc.api.patient.vaccine.application.port.in.FindVaccinePort;
import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.model.Vaccine;
import com.vet.hc.api.patient.vaccine.domain.repository.VaccineRepository;
import com.vet.hc.api.shared.domain.query.Result;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Use case to find a vaccine.
 */
@Slf4j
@NoArgsConstructor
public final class FindVaccineUseCase implements FindVaccinePort {
    private VaccineRepository vaccineRepository;

    private final VaccineMapper vaccineMapper = VaccineMapper.INSTANCE;

    @Inject
    public FindVaccineUseCase(VaccineRepository vaccineRepository) {
        this.vaccineRepository = vaccineRepository;
    }

    @Override
    public Result<VaccineDto, VaccineFailure> findById(Long id) {
        log.info("Finding vaccine by id `{}`", id);

        var result = vaccineRepository.findById(id);

        if (result.isEmpty()) {
            log.error("Vaccine not found with id `{}`", id);

            return Result.failure(VaccineFailure.NOT_FOUND);
        }

        Vaccine vaccine = result.get();

        log.info("Vaccine with id `{}` found`", vaccine.getId());

        return Result.success(vaccineMapper.toDto(vaccine));
    }
}
