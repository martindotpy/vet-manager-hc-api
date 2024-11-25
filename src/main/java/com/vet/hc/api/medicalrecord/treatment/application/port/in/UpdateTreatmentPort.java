package com.vet.hc.api.medicalrecord.treatment.application.port.in;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.UpdateTreatmentPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for updating a treatment.
 */
public interface UpdateTreatmentPort {
    /**
     * Update a treatment.
     *
     * @param payload Payload with the data to update the treatment.
     * @return Result with the treatment updated or the failure
     */
    Result<TreatmentDto, TreatmentFailure> update(UpdateTreatmentPayload payload);
}
