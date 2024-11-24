package com.vet.hc.api.patient.vaccine.application.port.in;

import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.payload.CreateVaccinePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a vaccine.
 */
public interface CreateVaccinePort {
    /**
     * Create a vaccine.
     *
     * @param payload Payload with the data to create the vaccine.
     * @return Result with the vaccine created or the failure
     */
    Result<VaccineDto, VaccineFailure> create(CreateVaccinePayload payload);
}
