package com.vet.hc.api.medicalrecord.treatment.application.port.in;

import com.vet.hc.api.medicalrecord.treatment.domain.dto.TreatmentDto;
import com.vet.hc.api.medicalrecord.treatment.domain.failure.TreatmentFailure;
import com.vet.hc.api.shared.domain.query.Result;

/**
 * Port for finding the treatment.
 */
public interface FindTreatmentPort {
    /**
     * Find the treatment by id.
     *
     * @param id the treatment id.
     * @return the treatment found or the failure
     */
    Result<TreatmentDto, TreatmentFailure> findById(Long id);
}
