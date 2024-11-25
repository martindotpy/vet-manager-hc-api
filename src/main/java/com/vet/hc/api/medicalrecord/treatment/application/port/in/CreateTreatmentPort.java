package com.vet.hc.api.medicalrecord.treatment.application.port.in;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.medicalrecord.treatment.domain.payload.CreateTreatmentPayload;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port to create a treatment.
 */
public interface CreateTreatmentPort {
    /**
     * Create a treatment.
     *
     * @param payload Payload with the data to create the treatment.
     * @return Result with the treatment created or the failure
     */
    Result<TreatmentDto, TreatmentFailure> create(CreateTreatmentPayload payload);
}
