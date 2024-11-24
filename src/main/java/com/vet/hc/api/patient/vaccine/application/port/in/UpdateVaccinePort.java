package com.vet.hc.api.patient.vaccine.application.port.in;

import com.vet.hc.api.patient.vaccine.domain.dto.VaccineDto;
import com.vet.hc.api.patient.vaccine.domain.failure.VaccineFailure;
import com.vet.hc.api.patient.vaccine.domain.payload.UpdateVaccinePayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a vaccine.
 */
public interface UpdateVaccinePort {
    /**
     * Update a vaccine.
     *
     * @param payload Payload with the data to update the vaccine.
     * @return Result with the vaccine updated or the failure
     */
    Result<VaccineDto, VaccineFailure> update(UpdateVaccinePayload payload);
}
